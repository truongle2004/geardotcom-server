package web_ecommerce.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DistrictDto {
    private Long id;
    private Integer code;
    private String name;
    private String codename;
    private String divisionType;
    private String shortCodename;
    private Integer provinceCode;
}
