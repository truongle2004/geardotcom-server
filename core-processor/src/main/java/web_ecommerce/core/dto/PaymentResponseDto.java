package web_ecommerce.core.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public class PaymentResponseDto {
    public String status;
    public String message;
    public String url;
}
