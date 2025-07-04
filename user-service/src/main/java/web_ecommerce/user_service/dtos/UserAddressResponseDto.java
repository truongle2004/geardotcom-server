package web_ecommerce.user_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressResponseDto {
    private Long id;
    private String userId;
    private String province;
    private String district;
    private String ward;
    private String fullAddress;
    private String addressType;
    private String phoneNumber;
    private String receiverName;
}
