package web_ecommerce.payment_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web_ecommerce.core.enums.PaymentHistoryAction;
import web_ecommerce.payment_service.dto.PaymentHistoryDto;
import web_ecommerce.payment_service.entities.PaymentHistory;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, String> {

    @Query("SELECT new web_ecommerce.payment_service.dto.PaymentHistoryDto(" +
            "ph.id, ph.paymentId, ph.orderId, ph.action, ph.previousStatus, ph.newStatus, " +
            "ph.amount, ph.transactionReference, ph.vnpTransactionNo, ph.responseCode, " +
            "ph.bankCode, ph.description, ph.metadata, ph.ipAddress, ph.createdAt, " +
            "ph.processingTimeMs, ph.errorMessage, ph.externalReference) " +
            "FROM PaymentHistory ph WHERE ph.paymentId = :paymentId " +
            "ORDER BY ph.createdAt DESC")
    List<PaymentHistoryDto> findByPaymentIdOrderByActionTimestampDesc(@Param("paymentId") String paymentId);

    @Query("SELECT new web_ecommerce.payment_service.dto.PaymentHistoryDto(" +
            "ph.id, ph.paymentId, ph.orderId, ph.action, ph.previousStatus, ph.newStatus, " +
            "ph.amount, ph.transactionReference, ph.vnpTransactionNo, ph.responseCode, " +
            "ph.bankCode, ph.description, ph.metadata, ph.ipAddress, ph.createdAt, " +
            "ph.processingTimeMs, ph.errorMessage, ph.externalReference) " +
            "FROM PaymentHistory ph WHERE ph.orderId = :orderId " +
            "ORDER BY ph.createdAt DESC")
    List<PaymentHistoryDto> findByOrderIdOrderByActionTimestampDesc(@Param("orderId") String orderId);

    @Query("SELECT new web_ecommerce.payment_service.dto.PaymentHistoryDto(" +
            "ph.id, ph.paymentId, ph.orderId, ph.action, ph.previousStatus, ph.newStatus, " +
            "ph.amount, ph.transactionReference, ph.vnpTransactionNo, ph.responseCode, " +
            "ph.bankCode, ph.description, ph.metadata, ph.ipAddress, ph.createdAt, " +
            "ph.processingTimeMs, ph.errorMessage, ph.externalReference) " +
            "FROM PaymentHistory ph WHERE ph.action = :action " +
            "AND ph.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY ph.createdAt DESC")
    List<PaymentHistoryDto> findByActionAndDateRange(
            @Param("action") PaymentHistoryAction action,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT new web_ecommerce.payment_service.dto.PaymentHistoryDto(" +
            "ph.id, ph.paymentId, ph.orderId, ph.action, ph.previousStatus, ph.newStatus, " +
            "ph.amount, ph.transactionReference, ph.vnpTransactionNo, ph.responseCode, " +
            "ph.bankCode, ph.description, ph.metadata, ph.ipAddress, ph.createdAt, " +
            "ph.processingTimeMs, ph.errorMessage, ph.externalReference) " +
            "FROM PaymentHistory ph WHERE ph.vnpTransactionNo = :vnpTransactionNo " +
            "ORDER BY ph.createdAt DESC")
    List<PaymentHistoryDto> findByVnpTransactionNo(@Param("vnpTransactionNo") String vnpTransactionNo);

    @Query("SELECT new web_ecommerce.payment_service.dto.PaymentHistoryDto(" +
            "ph.id, ph.paymentId, ph.orderId, ph.action, ph.previousStatus, ph.newStatus, " +
            "ph.amount, ph.transactionReference, ph.vnpTransactionNo, ph.responseCode, " +
            "ph.bankCode, ph.description, ph.metadata, ph.ipAddress, ph.createdAt, " +
            "ph.processingTimeMs, ph.errorMessage, ph.externalReference) " +
            "FROM PaymentHistory ph WHERE ph.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY ph.createdAt DESC")
    Page<PaymentHistoryDto> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT COUNT(ph) FROM PaymentHistory ph WHERE ph.paymentId = :paymentId")
    Long countByPaymentId(@Param("paymentId") String paymentId);

    @Query("SELECT ph FROM PaymentHistory ph WHERE ph.paymentId = :paymentId " +
            "AND ph.action = :action ORDER BY ph.createdAt DESC")
    List<PaymentHistory> findByPaymentIdAndAction(
            @Param("paymentId") String paymentId,
            @Param("action") PaymentHistoryAction action);
}
