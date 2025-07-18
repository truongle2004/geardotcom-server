package web_ecommerce.sale_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_ecommerce.sale_service.dto.CouponDto;
import web_ecommerce.sale_service.dto.CouponValidationResultDto;
import web_ecommerce.sale_service.dto.CreateCouponDto;
import web_ecommerce.sale_service.dto.ValidateCouponDto;
import web_ecommerce.sale_service.enitty.Coupon;
import web_ecommerce.sale_service.enitty.CouponUsage;
import web_ecommerce.sale_service.repository.CouponRepository;
import web_ecommerce.sale_service.repository.CouponUsageRepository;
import web_ecommerce.sale_service.service.CouponService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;

    @Override
    public CouponDto createCoupon(CreateCouponDto createCouponDto) {
        if (couponRepository.existsByCode(createCouponDto.getCode())) {
            throw new RuntimeException("Coupon code already exists: " + createCouponDto.getCode());
        }

        Coupon coupon = new Coupon();
        mapCreateDtoToEntity(createCouponDto, coupon);

        Coupon savedCoupon = couponRepository.save(coupon);
        return mapEntityToDto(savedCoupon);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponDto> getCouponById(String id) {
        return couponRepository.findByIdAsDto(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponDto> getCouponByCode(String code) {
        return couponRepository.findByCodeAsDto(code);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponDto> getAllCoupons(Pageable pageable) {
        return couponRepository.findAllAsDto(pageable);
    }

    @Override
    public CouponDto updateCoupon(String id, CreateCouponDto updateCouponDto) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));

        // Check if code is being changed and if new code already exists
        if (!coupon.getCode().equals(updateCouponDto.getCode()) &&
                couponRepository.existsByCode(updateCouponDto.getCode())) {
            throw new RuntimeException("Coupon code already exists: " + updateCouponDto.getCode());
        }

        mapCreateDtoToEntity(updateCouponDto, coupon);
        Coupon updatedCoupon = couponRepository.save(coupon);
        return mapEntityToDto(updatedCoupon);
    }

    @Override
    public void deleteCoupon(String id) {
        if (!couponRepository.existsById(id)) {
            throw new RuntimeException("Coupon not found with id: " + id);
        }
        couponRepository.deleteById(id);
    }

    @Override
    public void activateCoupon(String id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        coupon.setIsActive(true);
        couponRepository.save(coupon);
    }

    @Override
    public void deactivateCoupon(String id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        coupon.setIsActive(false);
        couponRepository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public CouponValidationResultDto validateCoupon(ValidateCouponDto validateCouponDto, String userId) {
        Optional<Coupon> couponOpt = couponRepository.findByCode(validateCouponDto.getCode());

        if (couponOpt.isEmpty()) {
            return CouponValidationResultDto.invalid("Coupon code not found");
        }

        Coupon coupon = couponOpt.get();
        OffsetDateTime now = OffsetDateTime.now();

        // Check if coupon is active
        if (!coupon.getIsActive()) {
            return CouponValidationResultDto.invalid("Coupon is not active");
        }

        // Check if coupon is within valid date range
        if (now.isBefore(coupon.getStartDate())) {
            return CouponValidationResultDto.invalid("Coupon is not yet valid");
        }

        if (now.isAfter(coupon.getEndDate())) {
            return CouponValidationResultDto.invalid("Coupon has expired");
        }

        // Check minimum amount requirement
        if (coupon.getMinimumAmount() != null &&
                validateCouponDto.getOrderAmount().compareTo(coupon.getMinimumAmount()) < 0) {
            return CouponValidationResultDto.invalid(
                    "Order amount must be at least " + coupon.getMinimumAmount());
        }

        // Check usage limit
        if (coupon.getUsageLimit() != null &&
                coupon.getUsageCount() >= coupon.getUsageLimit()) {
            return CouponValidationResultDto.invalid("Coupon usage limit has been reached");
        }

        // Check user usage limit
        Integer userUsageCount = couponUsageRepository.countByCouponIdAndUserId(
                coupon.getId(), userId);

        if (coupon.getUserLimit() != null && userUsageCount >= coupon.getUserLimit()) {
            return CouponValidationResultDto.invalid("You have reached the usage limit for this coupon");
        }

        // Calculate discount amount
        BigDecimal discountAmount = calculateDiscountAmount(coupon, validateCouponDto.getOrderAmount());

        return CouponValidationResultDto.valid(discountAmount, mapEntityToDto(coupon));
    }

    @Override
    public void useCoupon(String couponId, String userId, String orderId) {
        // Check if coupon usage already exists for this order
        if (couponUsageRepository.existsByCouponIdAndUserIdAndOrderId(couponId, userId, orderId)) {
            throw new RuntimeException("Coupon has already been used for this order");
        }

        // Increment coupon usage count
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + couponId));

        coupon.setUsageCount(coupon.getUsageCount() + 1);
        couponRepository.save(coupon);

        // Record coupon usage
        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setCouponId(couponId);
        couponUsage.setUserId(userId);
        couponUsage.setOrderId(orderId);
        couponUsageRepository.save(couponUsage);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCouponUsageCount(String couponId, String userId) {
        return couponUsageRepository.countByCouponIdAndUserId(couponId, userId);
    }

    private BigDecimal calculateDiscountAmount(Coupon coupon, BigDecimal orderAmount) {
        BigDecimal discountAmount;

        switch (coupon.getDiscountType()) {
            case PERCENTAGE:
                discountAmount = orderAmount.multiply(coupon.getDiscountValue())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                break;
            case FIXED_AMOUNT:
                discountAmount = coupon.getDiscountValue();
                break;
            default:
                discountAmount = BigDecimal.ZERO;
        }

        // Apply maximum discount limit if set
        if (coupon.getMaximumDiscount() != null &&
                discountAmount.compareTo(coupon.getMaximumDiscount()) > 0) {
            discountAmount = coupon.getMaximumDiscount();
        }

        // Ensure discount doesn't exceed order amount
        if (discountAmount.compareTo(orderAmount) > 0) {
            discountAmount = orderAmount;
        }

        return discountAmount;
    }

    private void mapCreateDtoToEntity(CreateCouponDto dto, Coupon entity) {
        entity.setCode(dto.getCode().toUpperCase().trim());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDiscountType(dto.getDiscountType());
        entity.setDiscountValue(dto.getDiscountValue());
        entity.setMinimumAmount(dto.getMinimumAmount());
        entity.setMaximumDiscount(dto.getMaximumDiscount());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setUsageLimit(dto.getUsageLimit());
        entity.setUserLimit(dto.getUserLimit());
        entity.setIsActive(dto.getIsActive());
    }

    private CouponDto mapEntityToDto(Coupon entity) {
        return new CouponDto(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getDiscountType(),
                entity.getDiscountValue(),
                entity.getMinimumAmount(),
                entity.getMaximumDiscount(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getUsageLimit(),
                entity.getUsageCount(),
                entity.getUserLimit(),
                entity.getIsActive()
        );
    }
}
