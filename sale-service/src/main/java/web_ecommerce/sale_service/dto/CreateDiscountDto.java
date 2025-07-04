package web_ecommerce.sale_service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web_ecommerce.core.enums.ApplicableToEnum;
import web_ecommerce.core.enums.DiscountTypeEnum;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiscountDto {
    private String name;
    private String description;
    private DiscountTypeEnum discountType;
    private BigDecimal discountValue;
    private BigDecimal minimumAmount = BigDecimal.ZERO;
    private BigDecimal maximumDiscount;
    private ApplicableToEnum applicableTo;
    private String productId;
    private String productCategoryId;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Integer usageLimit;
    private Boolean isActive = true;
}
