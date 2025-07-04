package web_ecommerce.user_service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.ResponseMessage;
import web_ecommerce.user_service.dtos.UserAddressDto;
import web_ecommerce.user_service.dtos.UserAddressResponseDto;
import web_ecommerce.user_service.dtos.UserProfileDto;
import web_ecommerce.user_service.entities.UserAddress;
import web_ecommerce.user_service.entities.UserProfile;
import web_ecommerce.user_service.repositories.UserAddressRepository;
import web_ecommerce.user_service.repositories.UserProfileRepository;
import web_ecommerce.user_service.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserProfileRepository userProfileRepository;
    private final UserAddressRepository userAddressRepository;

    @Override
    public Response<UserProfileDto> getProfileInfo(String userId) {
        return new Response<UserProfileDto>().withDataAndStatus(userProfileRepository.getByUserId(userId), HttpStatus.OK);
    }

    @Override
    public Response<String> updateProfile(UserProfileDto userProfileDto, String userId) {
        try {
            userProfileRepository.save(toEntity(userProfileDto, userId));
        } catch (Exception e) {
            return new Response<String>().withDataAndStatus("Cập nhật thông tin người dùng thành công!", HttpStatus.OK);
        }

        return new Response<String>().withDataAndStatus(ResponseMessage.UPDATE_USER_INFO_FAILED.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public Response<Page<UserAddressResponseDto>> getAddressInfo(String userId, Pageable pageable) {
        return new Response<Page<UserAddressResponseDto>>().withDataAndStatus(userAddressRepository.getByUserId(userId, pageable), HttpStatus.OK);
    }

    @Override
    public Response<String> updateAddress(UserAddressDto userAddressDto, String userId) {
        UserAddress userAddress = null;
        if (userAddressDto.getId() == null) {
            userAddress = toEntity(userAddressDto, userId);
        } else if (userAddress.getId() != null && userAddress.getId() > 0) {
            userAddress = userAddressRepository.getUserAddressById(userAddress.getId());
            if (userAddress != null) {
                if (userAddressDto.getProvinceCode() != null &&
                        !userAddressDto.getProvinceCode().equals(userAddress.getProvinceCode())) {
                    userAddress.setProvinceCode(userAddressDto.getProvinceCode());
                }

                if (userAddressDto.getDistrictCode() != null &&
                        !userAddressDto.getDistrictCode().equals(userAddress.getDistrictCode())) {
                    userAddress.setDistrictCode(userAddressDto.getDistrictCode());
                }

                if (userAddressDto.getWardCode() != null &&
                        !userAddressDto.getWardCode().equals(userAddress.getWardCode())) {
                    userAddress.setWardCode(userAddressDto.getWardCode());
                }

                if (userAddressDto.getFullAddress() != null &&
                        !userAddressDto.getFullAddress().equals(userAddress.getFullAddress())) {
                    userAddress.setFullAddress(userAddressDto.getFullAddress());
                }

                if (userAddressDto.getAddressType() != null &&
                        !userAddressDto.getAddressType().equals(userAddress.getAddressType())) {
                    userAddress.setAddressType(userAddressDto.getAddressType());
                }

                if (userAddressDto.getPhoneNumber() != null &&
                        !userAddressDto.getPhoneNumber().equals(userAddress.getPhoneNumber())) {
                    userAddress.setPhoneNumber(userAddressDto.getPhoneNumber());
                }

                if (userAddressDto.getReceiverName() != null &&
                        !userAddressDto.getReceiverName().equals(userAddress.getReceiverName())) {
                    userAddress.setReceiverName(userAddressDto.getReceiverName());
                }
            }
        }

        if (userAddress != null) userAddressRepository.save(userAddress);
        else return new Response<String>().withDataAndStatus("Cập nhật địa chỉ thất bại!", HttpStatus.BAD_REQUEST);

        return new Response<String>().withDataAndStatus("Cập nhật địa chỉ thành công!", HttpStatus.OK);
    }

    private UserProfile toEntity(UserProfileDto userProfileDto, String userId) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userProfileDto.getId());
        userProfile.setUserId(userId);
        userProfile.setBirthday(userProfileDto.getBirthday());
        userProfile.setGender(userProfileDto.getGender());
        userProfile.setPhone(userProfileDto.getPhone());
        return userProfile;
    }

    private UserAddress toEntity(UserAddressDto userAddressDto, String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(userAddressDto.getId());
        userAddress.setUserId(userId);
        userAddress.setProvinceCode(userAddressDto.getProvinceCode());
        userAddress.setDistrictCode(userAddressDto.getDistrictCode());
        userAddress.setWardCode(userAddressDto.getWardCode());
        userAddress.setFullAddress(userAddressDto.getFullAddress());
        userAddress.setAddressType(userAddressDto.getAddressType());
        userAddress.setPhoneNumber(userAddressDto.getPhoneNumber());
        userAddress.setReceiverName(userAddressDto.getReceiverName());
        return userAddress;
    }
}
