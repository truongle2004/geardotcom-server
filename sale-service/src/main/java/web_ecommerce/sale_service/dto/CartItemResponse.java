package web_ecommerce.sale_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    public String id;
    public String productId;
    public String productTitle;
    public String handle;
    public BigDecimal price;
    public int quantity;
    public String imageAlt;
    public String imageSrc;
}
