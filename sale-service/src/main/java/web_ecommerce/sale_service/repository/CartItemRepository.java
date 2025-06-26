package web_ecommerce.sale_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.dto.CartItemResponse;
import web_ecommerce.sale_service.enitty.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    List<CartItem> getAllByCartId(String cartId);

    @Query(value = "select new web_ecommerce.sale_service.dto.CartItemResponse(ci.id, p.id, p.title, p.handle, p.price, " +
            "ci.quantity, pi.alt, concat(:file_upload_url,'/', pi.src)) from Cart c " +
            "join CartItem ci on ci.cartId = c.id " +
            "join Product p on ci.productId = p.id " +
            "join ProductImage pi on p.id = pi.productId and pi.position = 1 " +
            "where c.userId = :userId")
    Page<List<CartItemResponse>> getAllCartItemByUserId(String userId, String file_upload_url, Pageable pageable);

    CartItem findByCartIdAndProductId(String cartId, String productId);

    @Query(value = "DELETE FROM cart_item ci WHERE EXISTS ci (SELECT 1 FROM cart c WHERE c.id = ci.cartId AND c.userId = :userId)", nativeQuery = true)
    void deleteAllByUserId(String userId);
}
