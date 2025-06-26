package web_ecommerce.sale_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendorDTO {
    public String id;
    public String name;
    public String handle;
    public String description;
}
