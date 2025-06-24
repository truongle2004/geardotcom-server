package web_ecommerce.sale_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDTO {
    public Long id;
    public String src;
    public String alt;
    public int position;

    public ProductImageDTO(Long id, String src, String alt, int position) {
        this.id = id;
        this.src = src;
        this.alt = alt;
        this.position = position;
    }
}
