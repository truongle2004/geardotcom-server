package web_ecommerce.sale_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.dto.DiscountDto;
import web_ecommerce.sale_service.enitty.Discount;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, String> {

    @Query("SELECT new web_ecommerce.sale_service.dto.DiscountDto(" +
            "d.id, d.name, d.description, d.discountType, d.discountValue, " +
            "d.minimumAmount, d.maximumDiscount, d.applicableTo, d.productId, " +
            "d.productCategoryId, d.startDate, d.endDate, d.usageLimit, " +
            "d.usageCount, d.isActive) " +
            "FROM Discount d")
    Page<DiscountDto> findAllAsDto(Pageable pageable);

    @Query("SELECT new web_ecommerce.sale_service.dto.DiscountDto(" +
            "d.id, d.name, d.description, d.discountType, d.discountValue, " +
            "d.minimumAmount, d.maximumDiscount, d.applicableTo, d.productId, " +
            "d.productCategoryId, d.startDate, d.endDate, d.usageLimit, " +
            "d.usageCount, d.isActive) " +
            "FROM Discount d " +
            "WHERE d.isActive = true AND d.startDate <= :currentDate AND d.endDate >= :currentDate")
    List<DiscountDto> findActiveDiscounts(@Param("currentDate") OffsetDateTime currentDate);

    @Query("SELECT new web_ecommerce.sale_service.dto.DiscountDto(" +
            "d.id, d.name, d.description, d.discountType, d.discountValue, " +
            "d.minimumAmount, d.maximumDiscount, d.applicableTo, d.productId, " +
            "d.productCategoryId, d.startDate, d.endDate, d.usageLimit, " +
            "d.usageCount, d.isActive) " +
            "FROM Discount d " +
            "WHERE d.isActive = true AND d.startDate <= :currentDate AND d.endDate >= :currentDate " +
            "AND (d.minimumAmount IS NULL OR d.minimumAmount <= :amount) " +
            "AND (d.usageLimit IS NULL OR d.usageCount < d.usageLimit) " +
            "AND (d.applicableTo = 'ALL_PRODUCTS' " +
            "     OR (d.applicableTo = 'SPECIFIC_PRODUCTS' AND d.productId = :productId) " +
            "     OR (d.applicableTo = 'CATEGORIES' AND d.productCategoryId = :categoryId))")
    List<DiscountDto> findApplicableDiscounts(
            @Param("productId") String productId,
            @Param("categoryId") String categoryId,
            @Param("amount") BigDecimal amount,
            @Param("currentDate") OffsetDateTime currentDate);

    @Query("SELECT new web_ecommerce.sale_service.dto.DiscountDto(" +
            "d.id, d.name, d.description, d.discountType, d.discountValue, " +
            "d.minimumAmount, d.maximumDiscount, d.applicableTo, d.productId, " +
            "d.productCategoryId, d.startDate, d.endDate, d.usageLimit, " +
            "d.usageCount, d.isActive) " +
            "FROM Discount d WHERE d.id = :id")
    DiscountDto findByIdAsDto(@Param("id") String id);
}
