package web_ecommerce.sale_service.enitty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import web_ecommerce.core.db.BaseEntityNonId;

import java.time.OffsetDateTime;

@Entity
@Table(name = "coupon_usages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponUsage extends BaseEntityNonId {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;


    @Column(name = "coupon_id", nullable = false, length = 36)
    private String couponId;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "order_id", length = 36)
    private String orderId;

    @Column(name = "used_at")
    private OffsetDateTime usedAt;
}
