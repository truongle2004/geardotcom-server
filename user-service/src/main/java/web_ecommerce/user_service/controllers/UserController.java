package web_ecommerce.user_service.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web_ecommerce.core.controller.BaseController;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.ResponseMessage;
import web_ecommerce.core.utils.StringUtils;
import web_ecommerce.user_service.dtos.UserAddressDto;
import web_ecommerce.user_service.dtos.UserProfileDto;
import web_ecommerce.user_service.services.UserService;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final static String root = "/user";
    private final UserService userService;


    @GetMapping(value = V1 + root + "/profile")
    public Response<?> getById(HttpServletRequest request) {
        String userId = getUserId(request);
        if (StringUtils.isNotNullOrEmpty(userId)) {
            return new Response<String>().withDataAndStatus(ResponseMessage.USER_PERMISSION_DENIED.getMessage(), HttpStatus.FORBIDDEN);
        }
        return userService.getProfileInfo(userId);
    }

    @PutMapping(value = V1 + root + "/profile")
    public Response<String> updateProfile(@RequestBody UserProfileDto userProfileDto, HttpServletRequest request) {
        String userId = getUserId(request);
        if (StringUtils.isNotNullOrEmpty(userId)) {
            return new Response<String>().withDataAndStatus(ResponseMessage.USER_PERMISSION_DENIED.getMessage(), HttpStatus.FORBIDDEN);
        }
        return userService.updateProfile(userProfileDto, userId);
    }

    @PutMapping(value = V1 + root + "/address")
    public Response<String> updateAddress(@RequestBody UserAddressDto userAddressDto, HttpServletRequest request) {
        String userId = getUserId(request);
        if (StringUtils.isNotNullOrEmpty(userId)) {
            return new Response<String>().withDataAndStatus(ResponseMessage.USER_PERMISSION_DENIED.getMessage(), HttpStatus.FORBIDDEN);
        }
        return userService.updateAddress(userAddressDto, userId);
    }

    @GetMapping(value = V1 + root + "/address")
    public Response<?> getAddress(HttpServletRequest request) {
        String userId = getUserId(request);
        if (StringUtils.isNotNullOrEmpty(userId)) {
            return new Response<String>().withDataAndStatus(ResponseMessage.USER_PERMISSION_DENIED.getMessage(), HttpStatus.FORBIDDEN);
        }
        return userService.getAddressInfo(userId);
    }
}
