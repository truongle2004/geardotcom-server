package web_ecommerce.sale_service.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import web_ecommerce.core.controller.BaseController;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.ResponseMessage;
import web_ecommerce.core.utils.StringUtils;
import web_ecommerce.sale_service.dto.CartItemDTO;
import web_ecommerce.sale_service.service.CartService;

@RestController
public class CartController extends BaseController {
    private final String root = "/sale/carts";

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @ApiOperation(value = "API add product to cart")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @PostMapping(value = V1 + root)
    public Response<?> addProductToCart(HttpServletRequest httpServletRequest,
                                        @RequestBody CartItemDTO cartItemDTO) {
        String userId = getUserId(httpServletRequest);
        if (StringUtils.isNotNullOrEmpty(userId))
            return new Response<String>().withDataAndStatus(ResponseMessage.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        return cartService.addItemToCart(userId, cartItemDTO);
    }

    @ApiOperation(value = "API remove product from cart")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @DeleteMapping(value = V1 + root + "/{id}")
    public Response<String> removeProductFromCart(HttpServletRequest httpServletRequest, @PathVariable String id) {
        String userId = getUserId(httpServletRequest);
        if (StringUtils.isNotNullOrEmpty(userId))
            return new Response<String>().withDataAndStatus(ResponseMessage.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        cartService.removeItemFromCart(id);
        return new Response<String>().withDataAndStatus("Remove product from cart successfully", HttpStatus.OK);
    }


    @ApiOperation(value = "API get all cart items")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @GetMapping(value = V1 + root)
    public Response<?> getAllCartItems(HttpServletRequest httpServletRequest, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable) {
        String userId = getUserId(httpServletRequest);
        if (StringUtils.isNotNullOrEmpty(userId)) {
            return new Response<>().withDataAndStatus(ResponseMessage.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }
        return cartService.getAllCartItemInfo(userId, pageable);
    }
}
