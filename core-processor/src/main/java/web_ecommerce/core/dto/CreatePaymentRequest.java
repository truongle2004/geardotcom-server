package web_ecommerce.core.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private long amount;
    private String orderInfo;
    private String orderType;
    private String locale;
    private String bankCode;
    private String userId;
}
