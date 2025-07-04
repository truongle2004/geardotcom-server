package web_ecommerce.core.enums;


import org.springframework.stereotype.Component;

public enum GenderEnum {
    MALE(1),
    FEMALE(2);

    private int value;

    GenderEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
