package web_ecommerce.core.dto;

import lombok.Data;

@Data
public class PaymentResponseDto {
    public String status;
    public String message;
    public String url;
}
