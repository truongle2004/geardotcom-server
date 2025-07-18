package web_ecommerce.sale_service.service;

import org.springframework.http.ResponseEntity;
import web_ecommerce.sale_service.dto.OrderRequestDto;

public interface OrderService {
    ResponseEntity<?> createOrder(OrderRequestDto orderRequestDto, String userId);
}
