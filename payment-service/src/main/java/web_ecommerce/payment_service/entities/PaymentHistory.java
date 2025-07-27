package web_ecommerce.payment_service.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import web_ecommerce.core.db.BaseEntityNonId;
import web_ecommerce.core.enums.PaymentHistoryAction;
import web_ecommerce.core.enums.PaymentStatus;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment_history")
public class PaymentHistory extends BaseEntityNonId {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private PaymentHistoryAction action;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private PaymentStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private PaymentStatus newStatus;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "vnp_transaction_no")
    private String vnpTransactionNo;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "description", length = 1000)
    private String description;

    @Lob
    @Column(name = "metadata")
    private String metadata;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "processing_time_ms")
    private Long processingTimeMs;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @Column(name = "external_reference")
    private String externalReference;

}
