package web_ecommerce.core.enums;

public enum PaymentStatus {
    SUCCESS(1),       // Giao dịch thành công
    FAILED(2),        // Giao dịch thất bại
    CANCELED(3),      // Giao dịch bị hủy bởi người dùng
    PENDING(4),       // Giao dịch đang chờ xử lý
    UNKNOWN(5);       // Không xác định

    private final int code;

    PaymentStatus(int code) {
        this.code = code;
    }

    public static PaymentStatus fromVnpCode(String vnpResponseCode) {
        switch (vnpResponseCode) {
            case "00":
                return SUCCESS;

            case "24": // User canceled
                return CANCELED;

            case "07": // Suspicious success
            case "09":
            case "10":
            case "11":
            case "12":
            case "13":
            case "51":
            case "65":
            case "75":
            case "79":
                return FAILED;

            case "99":
                return UNKNOWN;

            default:
                return UNKNOWN;
        }
    }

    public int getCode() {
        return code;
    }
}
