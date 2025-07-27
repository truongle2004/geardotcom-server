package web_ecommerce.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web_ecommerce.core.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String id;
    private String orderId;
    private String vnpTxnRef;
    private String vnpTransactionNo;
    private BigDecimal amount;
    private String bankCode;
    private String cardType;
    private LocalDateTime payDate;
    private String responseCode;
    private String transactionStatus;
    private PaymentStatus paymentStatus;
}
