package web_ecommerce.sale_service.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import web_ecommerce.core.dto.PaymentResponseDto;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.sale_service.dto.OrderRequestDto;

public interface OrderService {
    Response<?> createOrder(OrderRequestDto orderRequestDto, String userId);
}
