package web_ecommerce.sale_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import web_ecommerce.core.dto.CreatePaymentRequest;
import web_ecommerce.core.dto.PaymentResponseDto;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.ResponseMessage;
import web_ecommerce.sale_service.dto.CartItemDTO;
import web_ecommerce.sale_service.dto.OrderRequestDto;
import web_ecommerce.sale_service.repository.ProductRepository;
import web_ecommerce.sale_service.service.OrderService;
import web_ecommerce.sale_service.service.feign.PaymentClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final String DEFAULT_BANK_CODE = "NCB";
    private static final String DEFAULT_LOCALE = "vn";
    private static final String DEFAULT_ORDER_TYPE = "190000";
    private final PaymentClient paymentClient;
    private final ProductRepository productRepository;

    @Override
    public Response<?> createOrder(OrderRequestDto orderRequestDto, String userId) {

        if (userId == null) {
            return new Response<>().withDataAndStatus(ResponseMessage.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (orderRequestDto == null || orderRequestDto.getItems().isEmpty()) {
            return new Response<>().withDataAndStatus(ResponseMessage.ORDER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        }

        List<String> productIds = orderRequestDto.getItems().stream().map(CartItemDTO::getProductId).collect(Collectors.toList());

        BigDecimal totalPrice = productRepository.getTotalPrice(productIds);

        if (totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) == 0) {
            return new Response<>().withDataAndStatus(ResponseMessage.CREATE_ORDER_FAILED.getMessage(), HttpStatus.BAD_REQUEST);
        }

        UUID orderId = UUID.randomUUID();

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setBankCode(DEFAULT_BANK_CODE);
        request.setLocale(DEFAULT_LOCALE);
        request.setOrderInfo(orderId.toString());
        request.setOrderType(DEFAULT_ORDER_TYPE);
        request.setAmount(totalPrice.longValue());

        try {
            PaymentResponseDto paymentResponseDtoResponse = paymentClient.createPayment(request);
            return new Response<>().withDataAndStatus(paymentResponseDtoResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new Response<>().withDataAndStatus(ResponseMessage.CREATE_ORDER_FAILED.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
