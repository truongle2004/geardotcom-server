package web_ecommerce.sale_service.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web_ecommerce.core.controller.BaseController;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.sale_service.dto.CouponValidationResultDto;
import web_ecommerce.sale_service.dto.ValidateCouponDto;
import web_ecommerce.sale_service.service.CouponService;

@RestController
@RequiredArgsConstructor
public class CouponController extends BaseController {
    private static final String root = "coupon";
    private final CouponService couponService;

    @ApiOperation(value = "API get list product")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    @PostMapping(V1 + root + "/validate")
    public Response<?> validationResultDtoResponse(HttpServletRequest request, @RequestBody ValidateCouponDto validateCouponDto) {
        String userId = getUserId(request);
        CouponValidationResultDto couponValidationResultDto = couponService.validateCoupon(validateCouponDto, userId);
        return new Response<>().withDataAndStatus(couponValidationResultDto, HttpStatus.OK);
    }
}
