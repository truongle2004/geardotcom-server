package web_ecommerce.sale_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web_ecommerce.core.controller.BaseController;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.sale_service.dto.OrderRequestDto;
import web_ecommerce.sale_service.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController extends BaseController {
    private final static String root = "/sale/orders";
    private final OrderService orderService;

    @PostMapping(V1 + root + "/create-order")
    public Response<?> createOrder(HttpServletRequest request, @RequestBody OrderRequestDto orderRequestDto) {
        String userId = getUserId(request);
        return orderService.createOrder(orderRequestDto, userId);
    }
}
