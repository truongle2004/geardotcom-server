package web_ecommerce.sale_service.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web_ecommerce.core.controller.BaseController;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.ResponseMessage;
import web_ecommerce.core.utils.StringUtils;
import web_ecommerce.sale_service.dto.AddToWishlistDto;
import web_ecommerce.sale_service.service.WishlistService;

@RestController
@RequiredArgsConstructor
public class WishlistController extends BaseController {
    private static final String root = "/sale/wishlist";
    private final WishlistService wishlistService;

    @ApiOperation(value = "API get list product")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @PostMapping(value = V1 + root)
    public Response<?> addWishlist(HttpServletRequest httpServletRequest, @RequestBody AddToWishlistDto addToWishlistDto) {
        String userId = getUserId(httpServletRequest);
        if (StringUtils.isNotNullOrEmpty(userId)) {
            return new Response<>().withDataAndStatus(ResponseMessage.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }
        return wishlistService.addProductToWishlist(addToWishlistDto, userId);
    }

    
}
