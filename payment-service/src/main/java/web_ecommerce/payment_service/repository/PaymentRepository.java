package web_ecommerce.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web_ecommerce.payment_service.dto.PaymentDto;
import web_ecommerce.payment_service.entities.PaymentTransaction;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTransaction, String> {
    @Query(value = "select new web_ecommerce.payment_service.dto.PaymentDto( " +
            "p.id, p.orderId, p.vnpTxnRef, p.vnpTransactionNo, p.amount, p.bankCode, p.cardType, p.payDate, p.responseCode, p.transactionStatus, p.paymentStatus " +
            ") from PaymentTransaction p where p.id = :paymentId")
    PaymentDto findByPaymentId(String paymentId);


    @Query("SELECT p FROM PaymentTransaction p WHERE p.vnpTxnRef = :vnpTxnRef")
    PaymentTransaction findByVnpTxnRef(@Param("vnpTxnRef") String vnpTxnRef);
}
