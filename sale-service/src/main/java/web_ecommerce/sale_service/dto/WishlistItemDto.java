package web_ecommerce.sale_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemDto {
    private String id;
    private String wishlistId;
    private String productId;
    private String productTitle;
    private String productHandle;
    private BigDecimal productPrice;
    private Boolean productAvailable;
}
