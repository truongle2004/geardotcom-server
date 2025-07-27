package web_ecommerce.payment_service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.PaymentHistoryAction;
import web_ecommerce.core.enums.PaymentStatus;
import web_ecommerce.payment_service.dto.PaymentHistoryDto;
import web_ecommerce.payment_service.entities.PaymentTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PaymentHistoryService {

    void recordPaymentHistory(String paymentId, String orderId, PaymentHistoryAction action,
                              PaymentStatus previousStatus, PaymentStatus newStatus,
                              String description, Map<String, Object> metadata);

    void recordPaymentHistory(String paymentId, String orderId, PaymentHistoryAction action,
                              PaymentStatus previousStatus, PaymentStatus newStatus,
                              BigDecimal amount, String transactionReference,
                              String description, Map<String, Object> metadata);

    void recordPaymentInitiation(PaymentTransaction paymentTransaction, String description);

    void recordPaymentSuccess(PaymentTransaction paymentTransaction, Map<String, String> vnpayParams);

    void recordPaymentFailure(String paymentId, String orderId, String errorMessage,
                              Map<String, String> vnpayParams);

    Response<List<PaymentHistoryDto>> getPaymentHistory(String paymentId);

    Response<List<PaymentHistoryDto>> getOrderHistory(String orderId);

    Response<Page<PaymentHistoryDto>> getHistoryByDateRange(LocalDateTime startDate,
                                                            LocalDateTime endDate,
                                                            Pageable pageable);

    Response<List<PaymentHistoryDto>> getHistoryByAction(PaymentHistoryAction action,
                                                         LocalDateTime startDate,
                                                         LocalDateTime endDate);

    Response<Long> getHistoryCount(String paymentId);
}
