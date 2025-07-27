package web_ecommerce.payment_service.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import web_ecommerce.core.dto.CreatePaymentRequest;
import web_ecommerce.core.dto.PaymentResponseDto;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.PaymentHistoryAction;
import web_ecommerce.core.enums.PaymentStatus;
import web_ecommerce.core.enums.ResponseMessage;
import web_ecommerce.core.enums.VnpResponseCode;
import web_ecommerce.payment_service.config.PaymentProperties;
import web_ecommerce.payment_service.dto.PaymentDto;
import web_ecommerce.payment_service.entities.PaymentTransaction;
import web_ecommerce.payment_service.repository.PaymentRepository;
import web_ecommerce.payment_service.services.PaymentHistoryService;
import web_ecommerce.payment_service.services.PaymentService;
import web_ecommerce.payment_service.utils.PaymentUtils;
import web_ecommerce.payment_service.utils.VnpayParams;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private static final String VNP_VERSION = "2.1.0";
    private static final String VNP_COMMAND = "pay";
    private static final String ORDER_TYPE = "190000";
    private static final String VNP_IP_ADDR = "127.0.0.1";
    private static final String VNP_CURRENCY = "VND";
    private static final String VNP_BANK_CODE = "NCB";
    private static final String VNP_LOCALE = "vn";
    private static final long DEFAULT_AMOUNT = 100L;
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";

    private final PaymentProperties paymentProperties;
    private final PaymentRepository paymentRepository;
    private final PaymentUtils paymentUtils;
    private final PaymentHistoryService paymentHistoryService;

    @Override
    public PaymentResponseDto createPayment(CreatePaymentRequest request) {
        // Original logic - unchanged
        Map<String, String> vnpParams = buildVnpParams(request);
        String secureHash = buildSecureHash(vnpParams);
        String paymentUrl = paymentProperties.getVnpPayUrl() + "?" + buildQueryString(vnpParams) + "&vnp_SecureHash=" + secureHash;

        // ONLY ADD: Record payment URL generation in history (non-blocking)
        try {
            String orderId = request.getOrderInfo();
            BigDecimal amount = BigDecimal.valueOf(request.getAmount() * DEFAULT_AMOUNT);

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("orderInfo", request.getOrderInfo());
            metadata.put("amount", amount);
            metadata.put("vnpParams", vnpParams);

            paymentHistoryService.recordPaymentHistory(
                    null, // No payment ID yet
                    orderId,
                    PaymentHistoryAction.PAYMENT_INITIATED,
                    null,
                    PaymentStatus.PENDING,
                    amount,
                    request.getOrderInfo(),
                    "Payment URL generated for order: " + request.getOrderInfo(),
                    metadata
            );
        } catch (Exception e) {
            // Don't let history recording affect the main flow
            log.warn("Failed to record payment initiation history: {}", e.getMessage());
        }

        return PaymentResponseDto.builder()
                .message(VnpResponseCode.CODE_00.getMessage())
                .status(VnpResponseCode.CODE_00.getCode())
                .url(paymentUrl)
                .build();
    }

    @Override
    public Response<PaymentDto> returnPayment(String paymentId) {
        return new Response<PaymentDto>()
                .withDataAndStatus(paymentRepository.findByPaymentId(paymentId), HttpStatus.OK);
    }

    @Override
    public Response<?> paymentSuccessHandle(Map<String, String> params) {
        try {
            String responseCode = params.get(VnpayParams.vnp_ResponseCode);
            VnpResponseCode code = VnpResponseCode.fromCode(responseCode);

            if (code == VnpResponseCode.CODE_00) {
                // Original logic - unchanged
                String paymentId = saveSuccessfulPayment(params);

                // ONLY ADD: Record success in history (non-blocking)
                try {
                    PaymentTransaction savedPaymentTransaction = paymentRepository.findById(paymentId).orElse(null);
                    if (savedPaymentTransaction != null) {
                        paymentHistoryService.recordPaymentSuccess(savedPaymentTransaction, params);
                    }
                } catch (Exception e) {
                    log.warn("Failed to record payment success history: {}", e.getMessage());
                }

                return new Response<>().withDataAndStatus(paymentId, HttpStatus.OK);
            }

            // ONLY ADD: Record failure in history (non-blocking)
            try {
                String txnRef = params.get(VnpayParams.vnp_TxnRef);
                paymentHistoryService.recordPaymentFailure(null, txnRef, code.getMessage(), params);
            } catch (Exception e) {
                log.warn("Failed to record payment failure history: {}", e.getMessage());
            }

            return new Response<>().withDataAndStatus(code.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Payment handling failed: {}", e.getMessage(), e);

            // ONLY ADD: Record error in history (non-blocking)
            try {
                String orderId = params.get(VnpayParams.vnp_TxnRef);
                paymentHistoryService.recordPaymentFailure(null, orderId,
                        "Payment processing error: " + e.getMessage(), params);
            } catch (Exception historyException) {
                log.warn("Failed to record payment error history: {}", historyException.getMessage());
            }

            return new Response<>().withDataAndStatus(ResponseMessage.PAYMENT_UNKNOWN_ERROR.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private Map<String, String> buildVnpParams(CreatePaymentRequest request) {
        String txnRef = request.getOrderInfo();
        long amount = request.getAmount() * DEFAULT_AMOUNT;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String createDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.MINUTE, 15);
        String expireDate = sdf.format(calendar.getTime());

        Map<String, String> params = new TreeMap<>();
        params.put(VnpayParams.vnp_Version, VNP_VERSION);
        params.put(VnpayParams.vnp_Command, VNP_COMMAND);
        params.put(VnpayParams.vnp_TmnCode, paymentProperties.getVnpTmnCode());
        params.put(VnpayParams.vnp_Amount, String.valueOf(amount));
        params.put(VnpayParams.vnp_CurrCode, VNP_CURRENCY);
        params.put(VnpayParams.vnp_BankCode, VNP_BANK_CODE);
        params.put(VnpayParams.vnp_TxnRef, txnRef);
        params.put(VnpayParams.vnp_OrderInfo, "Thanh toan don hang:" + txnRef);
        params.put(VnpayParams.vnp_OrderType, ORDER_TYPE);
        params.put(VnpayParams.vnp_Locale, VNP_LOCALE);
        params.put(VnpayParams.vnp_IpAddr, VNP_IP_ADDR);
        params.put(VnpayParams.vnp_ReturnUrl, paymentProperties.getVnpReturnUrl());
        params.put(VnpayParams.vnp_CreateDate, createDate);
        params.put(VnpayParams.vnp_ExpireDate, expireDate);
        return params;
    }

    private String buildSecureHash(Map<String, String> vnpParams) {
        String hashData = vnpParams.entrySet().stream()
                .map(e -> encode(e.getKey()) + "=" + encode(e.getValue()))
                .reduce((a, b) -> a + "&" + b)
                .orElse("");
        return paymentUtils.hmacSHA512(paymentProperties.getVnpSecretKey(), hashData);
    }

    private String buildQueryString(Map<String, String> vnpParams) {
        return vnpParams.entrySet().stream()
                .map(e -> encode(e.getKey()) + "=" + encode(e.getValue()))
                .reduce((a, b) -> a + "&" + b)
                .orElse("");
    }

    private String encode(String input) {
        return URLEncoder.encode(input, StandardCharsets.US_ASCII);
    }

    private String saveSuccessfulPayment(Map<String, String> params) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setOrderId(params.get(VnpayParams.vnp_TxnRef));
        paymentTransaction.setVnpTxnRef(params.get(VnpayParams.vnp_TxnRef));
        paymentTransaction.setAmount(BigDecimal.valueOf(Long.parseLong(params.get(VnpayParams.vnp_Amount))));
        paymentTransaction.setVnpTransactionNo(params.get(VnpayParams.vnp_TransactionNo));
        paymentTransaction.setCreatedAt(LocalDateTime.now());
        paymentTransaction.setUpdatedAt(LocalDateTime.now());
        paymentTransaction.setBankCode(params.get(VnpayParams.vnp_BankCode));
        paymentTransaction.setCardType(params.get(VnpayParams.vnp_CardType));
        paymentTransaction.setPayDate(LocalDateTime.now());
        paymentTransaction.setResponseCode(params.get(VnpayParams.vnp_ResponseCode));
        paymentTransaction.setTransactionStatus(params.get(VnpayParams.vnp_TransactionStatus));
        paymentTransaction.setPaymentStatus(PaymentStatus.SUCCESS);

        PaymentTransaction saved = paymentRepository.save(paymentTransaction);
        return saved.getId();
    }
}
