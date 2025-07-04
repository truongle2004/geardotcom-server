package web_ecommerce.sale_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponValidationResultDto {
    private Boolean isValid;
    private String message;
    private BigDecimal discountAmount;
    private CouponDto coupon;

    public static CouponValidationResultDto invalid(String message) {
        return new CouponValidationResultDto(false, message, BigDecimal.ZERO, null);
    }

    public static CouponValidationResultDto valid(BigDecimal discountAmount, CouponDto coupon) {
        return new CouponValidationResultDto(true, "Coupon is valid", discountAmount, coupon);
    }
}
