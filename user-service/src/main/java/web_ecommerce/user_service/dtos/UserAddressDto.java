package web_ecommerce.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDto {
    private Long id;
    private String userId;
    private Integer provinceCode;
    private Integer districtCode;
    private Integer wardCode;
    private String fullAddress;
    private String addressType;
    private String phoneNumber;
    private String receiverName;
}
