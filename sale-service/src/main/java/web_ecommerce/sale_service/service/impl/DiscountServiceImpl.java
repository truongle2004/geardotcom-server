package web_ecommerce.sale_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web_ecommerce.core.enums.ApplicableToEnum;
import web_ecommerce.core.enums.DiscountTypeEnum;
import web_ecommerce.sale_service.dto.CreateDiscountDto;
import web_ecommerce.sale_service.dto.DiscountDto;
import web_ecommerce.sale_service.enitty.Discount;
import web_ecommerce.sale_service.repository.DiscountRepository;
import web_ecommerce.sale_service.service.DiscountService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    public DiscountDto createDiscount(CreateDiscountDto createDiscountDto) {
        log.info("Creating new discount: {}", createDiscountDto.getName());

        validateDiscountDto(createDiscountDto);

        Discount discount = mapToEntity(createDiscountDto);
        discount.setUsageCount(0);

        Discount savedDiscount = discountRepository.save(discount);

        log.info("Successfully created discount with ID: {}", savedDiscount.getId());
        return mapToDto(savedDiscount);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountDto> getDiscountById(String id) {
        log.debug("Fetching discount by ID: {}", id);

        DiscountDto discountDto = discountRepository.findByIdAsDto(id);
        return Optional.ofNullable(discountDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiscountDto> getAllDiscounts(Pageable pageable) {
        log.debug("Fetching all discounts with pagination: {}", pageable);

        return discountRepository.findAllAsDto(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountDto> getActiveDiscounts() {
        log.debug("Fetching active discounts");

        OffsetDateTime currentDate = OffsetDateTime.now();
        return discountRepository.findActiveDiscounts(currentDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountDto> getApplicableDiscountsForProduct(String productId, String categoryId, BigDecimal amount) {
        log.debug("Fetching applicable discounts for product: {}, category: {}, amount: {}",
                productId, categoryId, amount);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            // TODO: Handle negative amount
//            throw new BusinessException("Amount must be positive");
        }

        OffsetDateTime currentDate = OffsetDateTime.now();
        return discountRepository.findApplicableDiscounts(productId, categoryId, amount, currentDate);
    }

    @Override
    public DiscountDto updateDiscount(String id, CreateDiscountDto updateDiscountDto) {
        log.info("Updating discount with ID: {}", id);

        // TODO:
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow();

        validateDiscountDto(updateDiscountDto);

        // Update fields
        existingDiscount.setName(updateDiscountDto.getName());
        existingDiscount.setDescription(updateDiscountDto.getDescription());
        existingDiscount.setDiscountType(updateDiscountDto.getDiscountType());
        existingDiscount.setDiscountValue(updateDiscountDto.getDiscountValue());
        existingDiscount.setMinimumAmount(updateDiscountDto.getMinimumAmount());
        existingDiscount.setMaximumDiscount(updateDiscountDto.getMaximumDiscount());
        existingDiscount.setApplicableTo(updateDiscountDto.getApplicableTo());
        existingDiscount.setProductId(updateDiscountDto.getProductId());
        existingDiscount.setProductCategoryId(updateDiscountDto.getProductCategoryId());
        existingDiscount.setStartDate(updateDiscountDto.getStartDate());
        existingDiscount.setEndDate(updateDiscountDto.getEndDate());
        existingDiscount.setUsageLimit(updateDiscountDto.getUsageLimit());
        existingDiscount.setIsActive(updateDiscountDto.getIsActive());

        Discount updatedDiscount = discountRepository.save(existingDiscount);

        log.info("Successfully updated discount with ID: {}", id);
        return mapToDto(updatedDiscount);
    }

    @Override
    public void deleteDiscount(String id) {
        log.info("Deleting discount with ID: {}", id);

        if (!discountRepository.existsById(id)) {
            // TODO:
//            throw new ResourceNotFoundException("Discount not found with ID: " + id);
        }

        discountRepository.deleteById(id);
        log.info("Successfully deleted discount with ID: {}", id);
    }

    @Override
    public void activateDiscount(String id) {
        log.info("Activating discount with ID: {}", id);

        // TODO
        Discount discount = discountRepository.findById(id)
                .orElseThrow();

        discount.setIsActive(true);
        discountRepository.save(discount);

        log.info("Successfully activated discount with ID: {}", id);
    }

    @Override
    public void deactivateDiscount(String id) {
        log.info("Deactivating discount with ID: {}", id);

        // TODO
        Discount discount = discountRepository.findById(id)
                .orElseThrow();

        discount.setIsActive(false);
        discountRepository.save(discount);

        log.info("Successfully deactivated discount with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateDiscountAmount(String discountId, BigDecimal originalAmount, Integer quantity) {
        log.debug("Calculating discount amount for discount ID: {}, original amount: {}, quantity: {}",
                discountId, originalAmount, quantity);

        if (originalAmount == null || originalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            // TODO:
//            throw new BusinessException("Original amount must be positive");
        }

        if (quantity == null || quantity <= 0) {
            // TODO
//            throw new BusinessException("Quantity must be positive");
        }

        // TODO
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow();

        // Check if discount is active and within date range
        OffsetDateTime now = OffsetDateTime.now();
        if (!discount.getIsActive() ||
                discount.getStartDate().isAfter(now) ||
                discount.getEndDate().isBefore(now)) {
            log.warn("Discount {} is not active or outside date range", discountId);
            return BigDecimal.ZERO;
        }

        // Check usage limit
        if (discount.getUsageLimit() != null &&
                discount.getUsageCount() >= discount.getUsageLimit()) {
            log.warn("Discount {} has reached usage limit", discountId);
            return BigDecimal.ZERO;
        }

        // Check minimum amount
        if (discount.getMinimumAmount() != null &&
                originalAmount.compareTo(discount.getMinimumAmount()) < 0) {
            log.warn("Original amount {} is below minimum amount {} for discount {}",
                    originalAmount, discount.getMinimumAmount(), discountId);
            return BigDecimal.ZERO;
        }

        BigDecimal discountAmount = BigDecimal.ZERO;

        // Calculate discount based on type
        if (discount.getDiscountType() == DiscountTypeEnum.PERCENTAGE) {
            discountAmount = originalAmount.multiply(discount.getDiscountValue())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        } else if (discount.getDiscountType() == DiscountTypeEnum.FIXED_AMOUNT) {
            discountAmount = discount.getDiscountValue();
        }

        // Apply maximum discount limit
        if (discount.getMaximumDiscount() != null &&
                discountAmount.compareTo(discount.getMaximumDiscount()) > 0) {
            discountAmount = discount.getMaximumDiscount();
        }

        // Ensure discount doesn't exceed original amount
        if (discountAmount.compareTo(originalAmount) > 0) {
            discountAmount = originalAmount;
        }

        log.debug("Calculated discount amount: {} for discount ID: {}", discountAmount, discountId);
        return discountAmount;
    }

    private void validateDiscountDto(CreateDiscountDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            // TODO
//            throw new BusinessException("Discount name is required");
        }

        if (dto.getDiscountType() == null) {
            // TODO:
//            throw new BusinessException("Discount type is required");
        }

        if (dto.getDiscountValue() == null || dto.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
            // TODO
//            throw new BusinessException("Discount value must be positive");
        }

        if (dto.getDiscountType() == DiscountTypeEnum.PERCENTAGE &&
                dto.getDiscountValue().compareTo(new BigDecimal("100")) > 0) {
            // TODO
//            throw new BusinessException("Percentage discount cannot exceed 100%");
        }

        if (dto.getApplicableTo() == null) {
            // TODO
//            throw new BusinessException("Applicable to field is required");
        }

        if (dto.getApplicableTo() == ApplicableToEnum.SPECIFIC_PRODUCTS &&
                (dto.getProductId() == null || dto.getProductId().trim().isEmpty())) {
            // TODO
//            throw new BusinessException("Product ID is required for specific product discounts");
        }

        if (dto.getApplicableTo() == ApplicableToEnum.CATEGORIES &&
                (dto.getProductCategoryId() == null || dto.getProductCategoryId().trim().isEmpty())) {
            // TODO
//            throw new BusinessException("Product category ID is required for category discounts");
        }

        if (dto.getStartDate() == null) {
            // TODO
//            throw new BusinessException("Start date is required");
        }

        if (dto.getEndDate() == null) {
            // TODO
//            throw new BusinessException("End date is required");
        }

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            // TODO
//            throw new BusinessException("End date must be after start date");
        }

        if (dto.getUsageLimit() != null && dto.getUsageLimit() <= 0) {
        }

        if (dto.getMinimumAmount() != null && dto.getMinimumAmount().compareTo(BigDecimal.ZERO) < 0) {
        }

        if (dto.getMaximumDiscount() != null && dto.getMaximumDiscount().compareTo(BigDecimal.ZERO) <= 0) {
        }
    }

    private Discount mapToEntity(CreateDiscountDto dto) {
        Discount discount = new Discount();
        discount.setName(dto.getName());
        discount.setDescription(dto.getDescription());
        discount.setDiscountType(dto.getDiscountType());
        discount.setDiscountValue(dto.getDiscountValue());
        discount.setMinimumAmount(dto.getMinimumAmount());
        discount.setMaximumDiscount(dto.getMaximumDiscount());
        discount.setApplicableTo(dto.getApplicableTo());
        discount.setProductId(dto.getProductId());
        discount.setProductCategoryId(dto.getProductCategoryId());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setUsageLimit(dto.getUsageLimit());
        discount.setIsActive(dto.getIsActive());
        return discount;
    }

    private DiscountDto mapToDto(Discount discount) {
        DiscountDto dto = new DiscountDto();
        dto.setId(discount.getId());
        dto.setName(discount.getName());
        dto.setDescription(discount.getDescription());
        dto.setDiscountType(discount.getDiscountType());
        dto.setDiscountValue(discount.getDiscountValue());
        dto.setMinimumAmount(discount.getMinimumAmount());
        dto.setMaximumDiscount(discount.getMaximumDiscount());
        dto.setApplicableTo(discount.getApplicableTo());
        dto.setProductId(discount.getProductId());
        dto.setProductCategoryId(discount.getProductCategoryId());
        dto.setStartDate(discount.getStartDate());
        dto.setEndDate(discount.getEndDate());
        dto.setUsageLimit(discount.getUsageLimit());
        dto.setUsageCount(discount.getUsageCount());
        dto.setIsActive(discount.getIsActive());
        return dto;
    }
}
