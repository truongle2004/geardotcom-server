package web_ecommerce.core.enums;

public enum VnpResponseCode {
    CODE_00("00", "Giao dịch thành công"),
    CODE_07("07", "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường)"),
    CODE_09("09", "Thẻ/Tài khoản chưa đăng ký dịch vụ Internet Banking"),
    CODE_10("10", "Xác thực thông tin thẻ/tài khoản không đúng quá 3 lần"),
    CODE_11("11", "Đã hết hạn chờ thanh toán. Vui lòng thực hiện lại giao dịch"),
    CODE_12("12", "Thẻ/Tài khoản bị khóa"),
    CODE_13("13", "Nhập sai mật khẩu OTP quá số lần quy định"),
    CODE_24("24", "Khách hàng hủy giao dịch"),
    CODE_51("51", "Tài khoản không đủ số dư"),
    CODE_65("65", "Tài khoản vượt quá hạn mức giao dịch trong ngày"),
    CODE_75("75", "Ngân hàng thanh toán đang bảo trì"),
    CODE_79("79", "Nhập sai mật khẩu thanh toán quá số lần quy định"),
    CODE_99("99", "Lỗi chưa xác định hoặc các lỗi khác"),

    UNKNOWN("UNKNOWN", "Không xác định");

    private final String code;
    private final String message;

    VnpResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static VnpResponseCode fromCode(String code) {
        for (VnpResponseCode value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
