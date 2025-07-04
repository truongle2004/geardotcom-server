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
public class CreateCouponDto {
    private String code;
    private String name;
    private String description;
    private DiscountTypeEnum discountType;
    private BigDecimal discountValue;
    private BigDecimal minimumAmount = BigDecimal.ZERO;
    private BigDecimal maximumDiscount;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Integer usageLimit;
    private Integer userLimit = 1;
    private Boolean isActive = true;
}
