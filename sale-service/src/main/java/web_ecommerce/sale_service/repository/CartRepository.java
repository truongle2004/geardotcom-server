package web_ecommerce.sale_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.enitty.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    @Query(value = "select c.id from Cart c where c.userId = :userId")
    String getByUserId(String userId);
}
