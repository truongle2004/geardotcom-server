package web_ecommerce.payment_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.PaymentHistoryAction;
import web_ecommerce.core.enums.PaymentStatus;
import web_ecommerce.payment_service.dto.PaymentHistoryDto;
import web_ecommerce.payment_service.entities.PaymentTransaction;
import web_ecommerce.payment_service.entities.PaymentHistory;
import web_ecommerce.payment_service.repository.PaymentHistoryRepository;
import web_ecommerce.payment_service.services.PaymentHistoryService;
import web_ecommerce.payment_service.utils.VnpayParams;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private static final Logger log = LoggerFactory.getLogger(PaymentHistoryServiceImpl.class);
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void recordPaymentHistory(String paymentId, String orderId, PaymentHistoryAction action,
                                     PaymentStatus previousStatus, PaymentStatus newStatus,
                                     String description, Map<String, Object> metadata) {
        recordPaymentHistory(paymentId, orderId, action, previousStatus, newStatus,
                null, null, description, metadata);
    }

    @Override
    public void recordPaymentHistory(String paymentId, String orderId, PaymentHistoryAction action,
                                     PaymentStatus previousStatus, PaymentStatus newStatus,
                                     BigDecimal amount, String transactionReference,
                                     String description, Map<String, Object> metadata) {
        try {
            PaymentHistory history = PaymentHistory.builder()
                    .paymentId(paymentId)
                    .orderId(orderId)
                    .action(action)
                    .previousStatus(previousStatus)
                    .newStatus(newStatus)
                    .amount(amount)
                    .transactionReference(transactionReference)
                    .description(description)
                    .metadata(metadata != null ? objectMapper.writeValueAsString(metadata) : null)
                    .build();

            paymentHistoryRepository.save(history);
            log.info("Payment history recorded: paymentId={}, action={}", paymentId, action);
        } catch (JsonProcessingException e) {
            log.error("Error serializing metadata for payment history: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error recording payment history: {}", e.getMessage(), e);
        }
    }

    @Override
    public void recordPaymentInitiation(PaymentTransaction paymentTransaction, String description) {
        recordPaymentHistory(
                paymentTransaction.getId(),
                paymentTransaction.getOrderId(),
                PaymentHistoryAction.PAYMENT_INITIATED,
                null,
                PaymentStatus.PENDING,
                paymentTransaction.getAmount(),
                paymentTransaction.getVnpTxnRef(),
                description != null ? description : "Payment request initiated",
                null
        );
    }

    @Override
    public void recordPaymentSuccess(PaymentTransaction paymentTransaction, Map<String, String> vnpayParams) {
        try {
            PaymentHistory history = PaymentHistory.builder()
                    .paymentId(paymentTransaction.getId())
                    .orderId(paymentTransaction.getOrderId())
                    .action(PaymentHistoryAction.PAYMENT_SUCCESS)
                    .previousStatus(PaymentStatus.PENDING)
                    .newStatus(PaymentStatus.SUCCESS)
                    .amount(paymentTransaction.getAmount())
                    .transactionReference(paymentTransaction.getVnpTxnRef())
                    .vnpTransactionNo(paymentTransaction.getVnpTransactionNo())
                    .responseCode(paymentTransaction.getResponseCode())
                    .bankCode(paymentTransaction.getBankCode())
                    .description("Payment completed successfully via VNPay")
                    .metadata(objectMapper.writeValueAsString(vnpayParams))
                    .build();

            paymentHistoryRepository.save(history);
            log.info("Payment success recorded: paymentId={}, vnpTransactionNo={}",
                    paymentTransaction.getId(), paymentTransaction.getVnpTransactionNo());
        } catch (JsonProcessingException e) {
            log.error("Error serializing VNPay params: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error recording payment success: {}", e.getMessage(), e);
        }
    }

    @Override
    public void recordPaymentFailure(String paymentId, String orderId, String errorMessage,
                                     Map<String, String> vnpayParams) {
        try {
            String responseCode = vnpayParams != null ? vnpayParams.get(VnpayParams.vnp_ResponseCode) : null;
            String bankCode = vnpayParams != null ? vnpayParams.get(VnpayParams.vnp_BankCode) : null;
            String vnpTransactionNo = vnpayParams != null ? vnpayParams.get(VnpayParams.vnp_TransactionNo) : null;

            PaymentHistory history = PaymentHistory.builder()
                    .paymentId(paymentId)
                    .orderId(orderId)
                    .action(PaymentHistoryAction.PAYMENT_FAILED)
                    .previousStatus(PaymentStatus.PENDING)
                    .newStatus(PaymentStatus.FAILED)
                    .responseCode(responseCode)
                    .bankCode(bankCode)
                    .vnpTransactionNo(vnpTransactionNo)
                    .description("Payment failed")
                    .errorMessage(errorMessage)
                    .metadata(vnpayParams != null ? objectMapper.writeValueAsString(vnpayParams) : null)
                    .build();

            paymentHistoryRepository.save(history);
            log.info("Payment failure recorded: paymentId={}, responseCode={}", paymentId, responseCode);
        } catch (JsonProcessingException e) {
            log.error("Error serializing VNPay params for failure: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error recording payment failure: {}", e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Response<List<PaymentHistoryDto>> getPaymentHistory(String paymentId) {
        try {
            List<PaymentHistoryDto> history = paymentHistoryRepository
                    .findByPaymentIdOrderByActionTimestampDesc(paymentId);
            return new Response<List<PaymentHistoryDto>>().withDataAndStatus(history, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving payment history for paymentId {}: {}", paymentId, e.getMessage());
            return new Response<List<PaymentHistoryDto>>().withDataAndStatus(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Response<List<PaymentHistoryDto>> getOrderHistory(String orderId) {
        try {
            List<PaymentHistoryDto> history = paymentHistoryRepository
                    .findByOrderIdOrderByActionTimestampDesc(orderId);
            return new Response<List<PaymentHistoryDto>>().withDataAndStatus(history, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving order history for orderId {}: {}", orderId, e.getMessage());
            return new Response<List<PaymentHistoryDto>>().withDataAndStatus(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Response<Page<PaymentHistoryDto>> getHistoryByDateRange(LocalDateTime startDate,
                                                                   LocalDateTime endDate,
                                                                   Pageable pageable) {
        try {
            Page<PaymentHistoryDto> history = paymentHistoryRepository
                    .findByDateRange(startDate, endDate, pageable);
            return new Response<Page<PaymentHistoryDto>>().withDataAndStatus(history, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving history by date range: {}", e.getMessage());
            return new Response<Page<PaymentHistoryDto>>().withDataAndStatus(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Response<List<PaymentHistoryDto>> getHistoryByAction(PaymentHistoryAction action,
                                                                LocalDateTime startDate,
                                                                LocalDateTime endDate) {
        try {
            List<PaymentHistoryDto> history = paymentHistoryRepository
                    .findByActionAndDateRange(action, startDate, endDate);
            return new Response<List<PaymentHistoryDto>>().withDataAndStatus(history, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving history by action {}: {}", action, e.getMessage());
            return new Response<List<PaymentHistoryDto>>().withDataAndStatus(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Response<Long> getHistoryCount(String paymentId) {
        try {
            Long count = paymentHistoryRepository.countByPaymentId(paymentId);
            return new Response<Long>().withDataAndStatus(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting history for paymentId {}: {}", paymentId, e.getMessage());
            return new Response<Long>().withDataAndStatus(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
