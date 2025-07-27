package web_ecommerce.core.enums;

public enum PaymentHistoryAction {
    PAYMENT_INITIATED("Khởi tạo thanh toán"),
    PAYMENT_PROCESSING("Đang xử lý thanh toán"),
    PAYMENT_SUCCESS("Thanh toán thành công"),
    PAYMENT_FAILED("Thanh toán thất bại"),
    PAYMENT_CANCELLED("Thanh toán đã bị hủy"),
    PAYMENT_REFUNDED("Đã hoàn tiền"),
    PAYMENT_PARTIALLY_REFUNDED("Đã hoàn tiền một phần"),
    PAYMENT_EXPIRED("Thanh toán đã hết hạn"),
    VNPAY_CALLBACK_RECEIVED("Đã nhận callback từ VNPay"),
    PAYMENT_VERIFIED("Xác minh thanh toán hoàn tất"),
    PAYMENT_DISPUTED("Thanh toán đang tranh chấp"),
    REFUND_INITIATED("Khởi tạo hoàn tiền"),
    REFUND_COMPLETED("Hoàn tiền thành công"),
    REFUND_FAILED("Hoàn tiền thất bại");


    private final String description;

    PaymentHistoryAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
