package web_ecommerce.sale_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web_ecommerce.core.enums.DiscountTypeEnum;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
    private String id;
    private String code;
    private String name;
    private String description;
    private DiscountTypeEnum discountType;
    private BigDecimal discountValue;
    private BigDecimal minimumAmount;
    private BigDecimal maximumDiscount;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Integer usageLimit;
    private Integer usageCount;
    private Integer userLimit;
    private Boolean isActive;
}
