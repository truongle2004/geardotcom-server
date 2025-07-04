package web_ecommerce.core.enums;

public enum ResponseMessage {
    // NOT FOUND
    PRODUCT_NOT_FOUND("Oops! Không tìm thấy sản phẩm này"),
    USER_NOT_FOUND("Không tìm thấy tài khoản của bạn rồi"),
    ORDER_NOT_FOUND("Không tìm thấy đơn hàng của bạn"),
    ORDER_ITEM_NOT_FOUND("Không tìm thấy mục này trong đơn hàng"),
    PAYMENT_NOT_FOUND("Không tìm thấy thông tin thanh toán"),
    WISHLIST_NOT_FOUND("Bạn chưa có danh sách yêu thích nào"),
    WISHLIST_ITEM_NOT_FOUND("Không tìm thấy sản phẩm trong danh sách yêu thích"),
    PAYMENT_METHOD_NOT_FOUND("Không tìm thấy phương thức thanh toán"),
    CART_NOT_FOUND("Chưa có giỏ hàng nào được tạo"),

    // Already exists
    CART_ITEM_ALREADY_EXISTS("Sản phẩm này đã có trong giỏ hàng, bạn thử chọn sản phẩm khác nhé!"),
    WISHLIST_ITEM_ALREADY_EXISTS("Sản phẩm này đã có trong danh sách yêu thích rồi"),

    // Success
    WISHLIST_ITEM_ADDED_SUCCESS("Đã thêm sản phẩm vào danh sách yêu thích!"),
    WISHLIST_ITEM_REMOVED_SUCCESS("Đã xóa sản phẩm khỏi danh sách yêu thích!"),

    USER_PERMISSION_DENIED("Bạn không có quyền thực hiện thao tác này"),

    // Failed
    UPDATE_USER_INFO_FAILED("Cập nhật thông tin người dùng không thành công!"),
    ;


    private String message;

    ResponseMessage(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
