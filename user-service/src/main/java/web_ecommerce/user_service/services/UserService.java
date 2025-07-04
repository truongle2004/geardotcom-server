package web_ecommerce.user_service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.user_service.dtos.UserAddressDto;
import web_ecommerce.user_service.dtos.UserAddressResponseDto;
import web_ecommerce.user_service.dtos.UserProfileDto;

public interface UserService {
    Response<UserProfileDto> getProfileInfo(String userId);

    Response<String> updateProfile(UserProfileDto userProfileDto, String userId);

    Response<Page<UserAddressResponseDto>> getAddressInfo(String userId, Pageable pageable);

    Response<String> updateAddress(UserAddressDto userAddressDto, String userId);
}
