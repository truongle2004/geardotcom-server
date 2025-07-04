package web_ecommerce.user_service.services;

import web_ecommerce.core.dto.response.Response;
import web_ecommerce.user_service.dtos.UserAddressDto;
import web_ecommerce.user_service.dtos.UserProfileDto;

public interface UserService {
    Response<UserProfileDto> getProfileInfo(String userId);

    Response<String> updateProfile(UserProfileDto userProfileDto, String userId);

    Response<UserAddressDto> getAddressInfo(String userId);

    Response<String> updateAddress(UserAddressDto userAddressDto, String userId);
}
