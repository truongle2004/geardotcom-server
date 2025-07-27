package web_ecommerce.payment_service.services;

import web_ecommerce.core.dto.CreatePaymentRequest;
import web_ecommerce.core.dto.PaymentResponseDto;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.payment_service.dto.PaymentDto;

import java.util.Map;

public interface PaymentService {
    PaymentResponseDto createPayment(CreatePaymentRequest request);
    Response<PaymentDto> returnPayment(String paymentId);
    Response<?> paymentSuccessHandle(Map<String, String> allParams);
}
