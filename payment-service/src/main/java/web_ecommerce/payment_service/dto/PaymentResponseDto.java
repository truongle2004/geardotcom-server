package web_ecommerce.payment_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PaymentResponseDto implements Serializable {
    public String status;
    public String message;
    public String URL;
}
