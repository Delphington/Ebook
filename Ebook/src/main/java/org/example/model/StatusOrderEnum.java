package org.example.model;

import static org.example.controller.Controller.scanner;

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

    public static StatusOrderEnum getChosenOrderStatus() {
        StatusOrderEnum statusOrderEnum;
        while (true) {
            System.out.println("Выберите новый статус");
            System.out.println("[1] NEW");
            System.out.println("[2] DONE");
            System.out.println("[3] CANCEL");
            int x;
            try {
                x = Integer.parseInt(scanner.nextLine().trim());
                statusOrderEnum = StatusOrderEnum.fromValue(x);
                break;
            } catch (RuntimeException e) {
                System.out.println("Неверный ввод! Попробуйте еще раз!");
            }
        }
        return statusOrderEnum;
    }
}
