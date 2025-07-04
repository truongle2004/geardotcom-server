package web_ecommerce.sale_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {
    private String id;
    private String userId;
    private List<WishlistItemDto> items;
}
