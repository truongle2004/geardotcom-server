package web_ecommerce.payment_service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import web_ecommerce.core.dto.CreatePaymentRequest;
import web_ecommerce.core.dto.PaymentResponseDto;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.payment_service.config.PaymentProperties;
import web_ecommerce.payment_service.dto.PaymentDto;
import web_ecommerce.payment_service.entities.Payment;
import web_ecommerce.payment_service.repository.PaymentRepository;
import web_ecommerce.payment_service.services.PaymentService;
import web_ecommerce.payment_service.utils.PaymentUtils;
import web_ecommerce.payment_service.utils.VnpayParams;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import static web_ecommerce.payment_service.config.Config.hashAllFields;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final String VNP_VERSION = "2.1.0";
    private static final String VNP_COMMAND = "pay";
    private static final String ORDER_TYPE = "190000";
    private static final String VNP_IP_ADDR = "127.0.0.1";
    private static final String VNP_CURRENCY = "VND";
    private static final String VNP_BANK_CODE = "NCB";
    private static final String VNP_LOCALE = "vn";
    private static final String EQUAL = "=";
    private static final String AMPERSAND = "&";
    private static final Long DEFAULT_AMOUNT = 100L;
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private final PaymentProperties paymentProperties;
    private final PaymentRepository paymentRepository;
    private final PaymentUtils paymentUtils;

    @Override
    public PaymentResponseDto createPayment(CreatePaymentRequest request) {
        long amount = request.getAmount();
        String vnp_TxnRef = paymentUtils.getRandomNumber(8);
        String vnp_TmnCode = paymentProperties.getVnpTmnCode();

        // Tạo thời gian tạo và hết hạn
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String vnp_CreateDate = formatter.format(calendar.getTime());

        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());

        // Chuẩn bị tham số
        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put(VnpayParams.vnp_Version, VNP_VERSION);
        vnp_Params.put(VnpayParams.vnp_Command, VNP_COMMAND);
        vnp_Params.put(VnpayParams.vnp_TmnCode, vnp_TmnCode);
        vnp_Params.put(VnpayParams.vnp_Amount, String.valueOf(amount * DEFAULT_AMOUNT));
        vnp_Params.put(VnpayParams.vnp_CurrCode, VNP_CURRENCY);
        vnp_Params.put(VnpayParams.vnp_BankCode, VNP_BANK_CODE);
        vnp_Params.put(VnpayParams.vnp_TxnRef, vnp_TxnRef);
        vnp_Params.put(VnpayParams.vnp_OrderInfo, "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put(VnpayParams.vnp_OrderType, ORDER_TYPE);
        vnp_Params.put(VnpayParams.vnp_Locale, VNP_LOCALE);
        vnp_Params.put(VnpayParams.vnp_IpAddr, VNP_IP_ADDR);
        vnp_Params.put(VnpayParams.vnp_ReturnUrl, paymentProperties.getVnpReturnUrl());
        vnp_Params.put(VnpayParams.vnp_CreateDate, vnp_CreateDate);
        vnp_Params.put(VnpayParams.vnp_ExpireDate, vnp_ExpireDate);

        // Tạo query string và dữ liệu để hash
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        vnp_Params.forEach((key, value) -> {
            String encodedKey = URLEncoder.encode(key, StandardCharsets.US_ASCII);
            String encodedValue = URLEncoder.encode(value, StandardCharsets.US_ASCII);

            if (hashData.length() > 0) {
                hashData.append(AMPERSAND);
                query.append(AMPERSAND);
            }

            hashData.append(encodedKey).append(EQUAL).append(encodedValue);
            query.append(encodedKey).append(EQUAL).append(encodedValue);
        });

        // Tính secure hash
        String vnp_SecureHash = paymentUtils.hmacSHA512(paymentProperties.getVnpSecretKey(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        // Tạo URL thanh toán
        String paymentUrl = paymentProperties.getVnpPayUrl() + "?" + query;

        PaymentResponseDto response = new PaymentResponseDto();
        response.setMessage("Thanh toan thanh cong");
        response.setStatus("00");
        response.setUrl(paymentUrl);
        return response;
    }

    @Override
    public Response<PaymentDto> returnPayment(Map<String, String> allParams) {
        String vnp_SecureHash = allParams.remove("vnp_SecureHash");
        String calculatedHash = hashAllFields(allParams);

        if (!calculatedHash.equals(vnp_SecureHash)) {
            return new Response<PaymentDto>().withDataAndStatus(null, HttpStatus.BAD_REQUEST);
        }

        String status = allParams.get("vnp_ResponseCode");
        if ("00".equals(status)) {
            // Save Payment Info
            Payment payment = new Payment();
            payment.setTransactionId(allParams.get("vnp_TransactionNo"));
            payment.setOrderId(allParams.get("vnp_TxnRef"));
            payment.setAmount(new BigDecimal(allParams.get("vnp_Amount")).divide(BigDecimal.valueOf(DEFAULT_AMOUNT)));
            payment.setStatus("SUCCESS");
            payment.setGateway("VNPay");
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);

            PaymentDto paymentResponseDto = new PaymentDto();
            paymentResponseDto.setTransactionId(allParams.get("vnp_TransactionNo"));
            paymentResponseDto.setOrderId(allParams.get("vnp_TxnRef"));
            paymentResponseDto.setAmount(payment.getAmount());
            paymentResponseDto.setBankCode(allParams.get("vnp_BankCode"));
            paymentResponseDto.setCardType(allParams.get("vnp_CardType"));
            paymentResponseDto.setPaidAt(payment.getPaidAt().toString());
            paymentResponseDto.setMessage("Payment successful");
            return new Response<PaymentDto>().withDataAndStatus(paymentResponseDto, HttpStatus.OK);
        } else {
            return new Response<PaymentDto>().withDataAndStatus(null, HttpStatus.BAD_REQUEST);
        }
    }
}
