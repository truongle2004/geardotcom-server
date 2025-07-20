package web_ecommerce.sale_service.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import web_ecommerce.core.dto.CreatePaymentRequest;
import web_ecommerce.core.dto.PaymentResponseDto;

import static web_ecommerce.core.controller.BaseController.API_V1;

@FeignClient(value = "payment-service", url = "http://localhost:8082")
public interface PaymentClient {
    @PostMapping(API_V1 + "/payment/create_payment")
    PaymentResponseDto createPayment(@RequestBody CreatePaymentRequest createPaymentRequest);
}
