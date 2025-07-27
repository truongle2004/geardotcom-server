package web_ecommerce.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web_ecommerce.core.enums.PaymentHistoryAction;
import web_ecommerce.core.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentHistoryDto {
    private String id;
    private String paymentId;
    private String orderId;
    private PaymentHistoryAction action;
    private PaymentStatus previousStatus;
    private PaymentStatus newStatus;
    private BigDecimal amount;
    private String transactionReference;
    private String vnpTransactionNo;
    private String responseCode;
    private String bankCode;
    private String description;
    private String metadata;
    private String ipAddress;
    private LocalDateTime actionTimestamp;
    private Long processingTimeMs;
    private String errorMessage;
    private String externalReference;
}
