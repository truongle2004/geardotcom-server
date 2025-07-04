package web_ecommerce.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceDto {
    private Long id;
    private Integer code;
    private String name;
    private String codename;
    private String divisionType;
    private String shortCodename;
}
