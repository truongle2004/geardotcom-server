package web_ecommerce.core.enums;

public enum ResponseMessage {
    // NOT FOUND
    PRODUCT_NOT_FOUND("Product not found"),
    USER_NOT_FOUND("User not found"),
    ORDER_NOT_FOUND("Order not found"),
    ORDER_ITEM_NOT_FOUND("Order item not found"),
    PAYMENT_NOT_FOUND("Payment not found"),
    PAYMENT_METHOD_NOT_FOUND("Payment method not found"),
    CART_NOT_FOUND("Cart not found"),
    CART_ITEM_ALREADY_EXISTS("This product already exists in your cart, please choose another one"),
    ;

    private String message;

    ResponseMessage(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
