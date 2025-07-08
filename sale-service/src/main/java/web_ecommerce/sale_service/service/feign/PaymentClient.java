package web_ecommerce.sale_service.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import web_ecommerce.core.dto.InitPaymentRequest;
import web_ecommerce.core.dto.InitPaymentResponse;
import web_ecommerce.core.dto.response.Response;

@FeignClient(value = "payment-service", url = "http://localhost:5000")
public interface PaymentClient {
    @PostMapping(value = "/api/v1/payment/ipn")
    Response<InitPaymentResponse> init(@RequestBody InitPaymentRequest request);
}
