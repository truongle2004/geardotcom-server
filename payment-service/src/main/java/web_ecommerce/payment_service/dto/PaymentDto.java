package web_ecommerce.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String transactionId;
    private String orderId;
    private BigDecimal amount;
    private String bankCode;
    private String cardType;
    private String paidAt;
    private String message;
}
