package web_ecommerce.payment_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web_ecommerce.core.controller.BaseController;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.PaymentHistoryAction;
import web_ecommerce.payment_service.dto.PaymentHistoryDto;
import web_ecommerce.payment_service.services.PaymentHistoryService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentHistoryController extends BaseController {

    public static final String root = "/payment-history";
    private final PaymentHistoryService paymentHistoryService;

    @GetMapping(V1 + root + "/payment/{paymentId}")
    public Response<List<PaymentHistoryDto>> getPaymentHistory(@PathVariable String paymentId) {
        return paymentHistoryService.getPaymentHistory(paymentId);
    }

    @GetMapping(V1 + root + "/order/{orderId}")
    public Response<List<PaymentHistoryDto>> getOrderHistory(@PathVariable String orderId) {
        return paymentHistoryService.getOrderHistory(orderId);
    }

    @GetMapping(V1 + root + "/date-range")
    public Response<Page<PaymentHistoryDto>> getHistoryByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return paymentHistoryService.getHistoryByDateRange(startDate, endDate, pageable);
    }

    @GetMapping(V1 + root + "/action/{action}")
    public Response<List<PaymentHistoryDto>> getHistoryByAction(
            @PathVariable PaymentHistoryAction action,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return paymentHistoryService.getHistoryByAction(action, startDate, endDate);
    }

    @GetMapping(V1 + root + "/count/{paymentId}")
    public Response<Long> getHistoryCount(@PathVariable String paymentId) {
        return paymentHistoryService.getHistoryCount(paymentId);
    }
}
