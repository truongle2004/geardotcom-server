package web_ecommerce.payment_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Config {
    @Value("${payment.vnp_PayUrl}")
    private String vnp_PayUrl;
    @Value("${payment.vnp_TmnCode}")
    private String vnp_TmnCode;
    @Value("${payment.vnp_SecretKey}")
    private String secretKey;
    @Value("${payment.vnp_ApiUrl}")
    private String vnp_ApiUrl;
    @Value("${payment.vnp_ReturnUrl}")
    private String vnp_ReturnUrl;

    // Rest of the methods (md5, Sha256, hashAllFields, hmacSHA512, getIpAddress, getRandomNumber) remain unchanged
    // Note: Update hashAllFields to use instance field secretKey
    public static String hashAllFields(Map fields) {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        // Note: This method is static, so you'll need to inject Config or pass secretKey
        // For simplicity, you might make this method non-static or pass secretKey explicitly
        throw new UnsupportedOperationException("Cannot access secretKey in static context. Consider making this method non-static.");
    }

    // Getters for accessing fields
    public String getVnp_PayUrl() {
        return vnp_PayUrl;
    }

    public String getVnp_TmnCode() {
        return vnp_TmnCode;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getVnp_ApiUrl() {
        return vnp_ApiUrl;
    }

    public String getVnp_ReturnUrl() {
        return vnp_ReturnUrl;
    }

    // ... other methods ...
}
