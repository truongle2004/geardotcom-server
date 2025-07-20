package web_ecommerce.payment_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {
    private String vnpTmnCode;
    private String vnpPayUrl;
    private String vnpSecretKey;
    private String vnpApiUrl;
    private String vnpReturnUrl;

    // Getters and setters
    public String getVnpTmnCode() {
        return vnpTmnCode;
    }

    public void setVnpTmnCode(String vnpTmnCode) {
        this.vnpTmnCode = vnpTmnCode;
    }

    public String getVnpPayUrl() {
        return vnpPayUrl;
    }

    public void setVnpPayUrl(String vnpPayUrl) {
        this.vnpPayUrl = vnpPayUrl;
    }

    public String getVnpSecretKey() {
        return vnpSecretKey;
    }

    public void setVnpSecretKey(String vnpSecretKey) {
        this.vnpSecretKey = vnpSecretKey;
    }

    public String getVnpApiUrl() {
        return vnpApiUrl;
    }

    public void setVnpApiUrl(String vnpApiUrl) {
        this.vnpApiUrl = vnpApiUrl;
    }

    public String getVnpReturnUrl() {
        return vnpReturnUrl;
    }

    public void setVnpReturnUrl(String vnpReturnUrl) {
        this.vnpReturnUrl = vnpReturnUrl;
    }
}
