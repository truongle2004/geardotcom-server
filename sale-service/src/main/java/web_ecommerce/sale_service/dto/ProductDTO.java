package web_ecommerce.sale_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    public String id;
    public String handle;
    public String description;
    public String title;
    public String warehouseId;
    public Long productVendorId;
    public Long productCategoryId;
    public String publishedScope;
    public Integer purchaseCount;
    public BigDecimal averageRating;
    public Integer reviewCount;
    public String tags;
    public BigDecimal price;
    public String productImage;
    public String imageAlt;
    public List<ProductImageDTO> images;
    public Integer soleQuantity;
    public Boolean notAllowPromotion;
    public Boolean available;


    public ProductDTO(String id, String handle,String title, String warehouseId,
                      Long productVendorId, Long productCategoryId, String publishedScope,
                      Integer purchaseCount, BigDecimal averageRating,
                      Integer reviewCount, String tags, Integer soleQuantity,
                      Boolean notAllowPromotion, Boolean available,String image, String alt, BigDecimal price) {
        this.id = id;
        this.handle = handle;
        this.title = title;
        this.warehouseId = warehouseId;
        this.productVendorId = productVendorId;
        this.productCategoryId = productCategoryId;
        this.publishedScope = publishedScope;
        this.purchaseCount = purchaseCount;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.tags = tags;
        this.soleQuantity = soleQuantity;
        this.notAllowPromotion = notAllowPromotion;
        this.available = available;
        this.productImage = image;
        this.imageAlt = alt;
        this.price = price;
    }
}