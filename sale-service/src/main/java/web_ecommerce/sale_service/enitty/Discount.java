package web_ecommerce.sale_service.enitty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import web_ecommerce.core.db.BaseEntityNonId;
import web_ecommerce.core.enums.ApplicableToEnum;
import web_ecommerce.core.enums.DiscountTypeEnum;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends BaseEntityNonId {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountTypeEnum discountType;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "minimum_amount", precision = 10, scale = 2)
    private BigDecimal minimumAmount = BigDecimal.ZERO;

    @Column(name = "maximum_discount", precision = 10, scale = 2)
    private BigDecimal maximumDiscount;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicable_to", nullable = false)
    private ApplicableToEnum applicableTo;

    @Column(name = "product_id", length = 36)
    private String productId;

    @Column(name = "product_category_id", length = 36)
    private String productCategoryId;

    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private OffsetDateTime endDate;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "usage_count")
    private Integer usageCount = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;


}

