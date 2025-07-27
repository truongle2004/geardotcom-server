package web_ecommerce.sale_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import web_ecommerce.core.dto.CreatePaymentRequest;
import web_ecommerce.core.dto.PaymentResponseDto;
import web_ecommerce.core.dto.response.Response;
import web_ecommerce.core.enums.OrderStatus;
import web_ecommerce.core.enums.ResponseMessage;
import web_ecommerce.sale_service.dto.CartItemDTO;
import web_ecommerce.sale_service.dto.OrderRequestDto;
import web_ecommerce.sale_service.enitty.Order;
import web_ecommerce.sale_service.enitty.OrderItem;
import web_ecommerce.sale_service.enitty.Product;
import web_ecommerce.sale_service.repository.OrderItemRepository;
import web_ecommerce.sale_service.repository.OrderRepository;
import web_ecommerce.sale_service.repository.ProductRepository;
import web_ecommerce.sale_service.service.OrderService;
import web_ecommerce.sale_service.service.feign.PaymentClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final String DEFAULT_BANK_CODE = "NCB";
    private static final String DEFAULT_LOCALE = "vn";
    private static final String DEFAULT_ORDER_TYPE = "190000";
    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
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

        List<Product> products = productRepository.findAllById(productIds);

        BigDecimal totalPrice = products.stream().reduce(
                BigDecimal.ZERO,
                (total, product) -> {
                    for (CartItemDTO item : orderRequestDto.getItems()) {
                        if (item.getProductId().equals(product.getId())) {
                            return total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                        }
                    }
                    return total;
                },
                BigDecimal::add
        );


        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setTotalAmount(totalPrice);
        newOrder.setUserId(userId);

        orderRepository.save(newOrder);

        List<OrderItem> orderItems = orderRequestDto.getItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrderId(newOrder.getId());
            return orderItem;
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);

        if (totalPrice == null || totalPrice.compareTo(BigDecimal.ZERO) == 0) {
            return new Response<>().withDataAndStatus(ResponseMessage.CREATE_ORDER_FAILED.getMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            CreatePaymentRequest request = new CreatePaymentRequest();
            request.setBankCode(DEFAULT_BANK_CODE);
            request.setLocale(DEFAULT_LOCALE);
            request.setOrderInfo(newOrder.getId());
            request.setOrderType(DEFAULT_ORDER_TYPE);
            request.setAmount(totalPrice.longValue());

            PaymentResponseDto paymentResponseDtoResponse = paymentClient.createPayment(request);
            return new Response<>().withDataAndStatus(paymentResponseDtoResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new Response<>().withDataAndStatus(ResponseMessage.CREATE_ORDER_FAILED.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
