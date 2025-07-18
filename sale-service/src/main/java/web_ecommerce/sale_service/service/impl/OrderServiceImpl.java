package web_ecommerce.sale_service.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import web_ecommerce.sale_service.dto.OrderRequestDto;
import web_ecommerce.sale_service.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public ResponseEntity<?> createOrder(OrderRequestDto orderRequestDto, String userId) {
        return null;
    }
}
