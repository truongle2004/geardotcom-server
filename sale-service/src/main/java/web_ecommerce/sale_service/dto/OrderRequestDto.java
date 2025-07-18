package web_ecommerce.sale_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    public List<String> productIds;
}
