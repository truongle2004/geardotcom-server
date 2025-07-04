package web_ecommerce.sale_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.enitty.CouponUsage;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, String> {

    @Query("SELECT COUNT(cu) FROM CouponUsage cu WHERE cu.couponId = :couponId AND cu.userId = :userId")
    Integer countByCouponIdAndUserId(@Param("couponId") String couponId, @Param("userId") String userId);

    boolean existsByCouponIdAndUserIdAndOrderId(String couponId, String userId, String orderId);
}
