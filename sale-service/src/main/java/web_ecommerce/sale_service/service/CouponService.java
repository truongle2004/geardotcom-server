package web_ecommerce.sale_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web_ecommerce.sale_service.dto.CouponDto;
import web_ecommerce.sale_service.dto.CouponValidationResultDto;
import web_ecommerce.sale_service.dto.CreateCouponDto;
import web_ecommerce.sale_service.dto.ValidateCouponDto;

import java.util.Optional;

public interface CouponService {
    CouponDto createCoupon(CreateCouponDto createCouponDto);

    Optional<CouponDto> getCouponById(String id);

    Optional<CouponDto> getCouponByCode(String code);

    Page<CouponDto> getAllCoupons(Pageable pageable);

    CouponDto updateCoupon(String id, CreateCouponDto updateCouponDto);

    void deleteCoupon(String id);

    void activateCoupon(String id);

    void deactivateCoupon(String id);

    CouponValidationResultDto validateCoupon(ValidateCouponDto validateCouponDto);

    void useCoupon(String couponId, String userId, String orderId);

    Integer getCouponUsageCount(String couponId, String userId);
}
