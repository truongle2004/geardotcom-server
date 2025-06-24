package web_ecommerce.sale_service.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.sale_service.dto.CartItemDTO;
import web_ecommerce.sale_service.dto.CartItemResponse;

import java.util.List;

public interface CartService {

    void clearMultipleCart(String userId, List<String> ids);

    Response<?> getAllCartItemInfo(String userId, Pageable pageable);

    void clearAllCartItem(String userId);

    Response<?> addItemToCart(String userId, CartItemDTO newItem);

    void removeItemFromCart(String cartId);
}
