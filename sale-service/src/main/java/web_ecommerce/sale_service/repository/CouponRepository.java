package web_ecommerce.sale_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.dto.CouponDto;
import web_ecommerce.sale_service.enitty.Coupon;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {

    @Query("SELECT new web_ecommerce.sale_service.dto.CouponDto(" +
            "c.id, c.code, c.name, c.description, c.discountType, c.discountValue, " +
            "c.minimumAmount, c.maximumDiscount, c.startDate, c.endDate, " +
            "c.usageLimit, c.usageCount, c.userLimit, c.isActive) " +
            "FROM Coupon c")
    Page<CouponDto> findAllAsDto(Pageable pageable);

    @Query("SELECT new web_ecommerce.sale_service.dto.CouponDto(" +
            "c.id, c.code, c.name, c.description, c.discountType, c.discountValue, " +
            "c.minimumAmount, c.maximumDiscount, c.startDate, c.endDate, " +
            "c.usageLimit, c.usageCount, c.userLimit, c.isActive) " +
            "FROM Coupon c WHERE c.id = :id")
    Optional<CouponDto> findByIdAsDto(@Param("id") String id);

    @Query("SELECT new web_ecommerce.sale_service.dto.CouponDto(" +
            "c.id, c.code, c.name, c.description, c.discountType, c.discountValue, " +
            "c.minimumAmount, c.maximumDiscount, c.startDate, c.endDate, " +
            "c.usageLimit, c.usageCount, c.userLimit, c.isActive) " +
            "FROM Coupon c WHERE c.code = :code")
    Optional<CouponDto> findByCodeAsDto(@Param("code") String code);

    Optional<Coupon> findByCode(String code);

    boolean existsByCode(String code);
}
