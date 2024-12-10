package org.example;

public enum StatusOrderEnum {
    NEW(1),
    DONE(2),
    CANCEL(3);
    private final int value;

    StatusOrderEnum(int value) {
        this.value = value;
    }

    public static StatusOrderEnum fromValue(int value) {
        for (StatusOrderEnum status : StatusOrderEnum.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неверный статус: " + value);
    }
}
