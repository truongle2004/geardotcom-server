package web_ecommerce.core.enums;


public enum ImageEnum {
    MAIN_IMAGE(1);

    private int value;

    ImageEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
