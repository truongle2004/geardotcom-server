package web_ecommerce.payment_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import web_ecommerce.core.controller.BaseController;
import web_ecommerce.core.dto.CreatePaymentRequest;
import web_ecommerce.core.dto.PaymentResponseDto;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.payment_service.dto.PaymentDto;
import web_ecommerce.payment_service.services.PaymentService;

import java.util.Map;

@RestController()
@RequiredArgsConstructor
public class PaymentController extends BaseController {
    public static final String root = "/payment";
    public final PaymentService paymentService;

    @PostMapping(V1 + root + "/create_payment")
    public PaymentResponseDto createPayment(@RequestBody CreatePaymentRequest request) {
        return paymentService.createPayment(request);
    }

    @GetMapping(V1 + root + "/vnpay_return")
    public Response<PaymentDto> vnpayReturn(@RequestParam Map<String, String> allParams) {
        return paymentService.returnPayment(allParams);
    }
}
