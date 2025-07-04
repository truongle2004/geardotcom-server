package web_ecommerce.sale_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web_ecommerce.sale_service.dto.CreateDiscountDto;
import web_ecommerce.sale_service.dto.DiscountDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DiscountService {
    DiscountDto createDiscount(CreateDiscountDto createDiscountDto);

    Optional<DiscountDto> getDiscountById(String id);

    Page<DiscountDto> getAllDiscounts(Pageable pageable);

    List<DiscountDto> getActiveDiscounts();

    List<DiscountDto> getApplicableDiscountsForProduct(String productId, String categoryId, BigDecimal amount);

    DiscountDto updateDiscount(String id, CreateDiscountDto updateDiscountDto);

    void deleteDiscount(String id);

    void activateDiscount(String id);

    void deactivateDiscount(String id);

    BigDecimal calculateDiscountAmount(String discountId, BigDecimal originalAmount, Integer quantity);
}
