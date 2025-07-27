package web_ecommerce.sale_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web_ecommerce.sale_service.enitty.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
