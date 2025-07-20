package web_ecommerce.payment_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import web_ecommerce.core.db.BaseEntityNonId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment extends BaseEntityNonId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private String transactionId;
    private String gateway;
    private BigDecimal amount;
    private String status;
    private LocalDateTime paidAt;
}
