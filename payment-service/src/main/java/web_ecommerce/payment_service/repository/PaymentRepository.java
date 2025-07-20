package web_ecommerce.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web_ecommerce.payment_service.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
