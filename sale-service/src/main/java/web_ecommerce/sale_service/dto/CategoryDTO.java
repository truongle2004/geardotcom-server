package web_ecommerce.sale_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDTO {
    public String id;
    public String name;
    public String handle;
    public String description;
}
