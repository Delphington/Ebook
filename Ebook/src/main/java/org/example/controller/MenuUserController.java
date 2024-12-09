package org.example.controller;

import org.example.Manager;

public class MenuUserController implements Controller {

    private OrderController orderController;
    private BookController bookController;
    private Manager manager;

    public MenuUserController(Manager manager) {
        this.manager = manager;
        bookController = new BookController(manager);
        orderController = new OrderController(manager);
    }

    @Override
    public ActionType run() {
        while (true) {
            showMenu();
            ActionType actionType = input();
            if (actionType == ActionType.EXIT) {
                break;
            }
        }
        return ActionType.MAIN_MENU;
    }

    @Override
    public ActionType input() {
        ActionType actionType = ActionType.MAIN_MENU;
        int temp = parseStringToInteger();
        switch (temp) {
            case 1 -> actionType = bookController.run();
            case 2 -> actionType = orderController.run();
            default -> {
                actionType = ActionType.MAIN_MENU;
                printStream.println("Неверный выбор! Попробуйте еще раз");
            }
        }
        return actionType;
    }

    @Override
    public void showMenu() {
        printStream.println("==============================");
        printStream.println("=======    Main menu   =======");
        printStream.println("==============================");
        printStream.println("[1] Работа с книгами");
        printStream.println("[2] Работа с заказами");
    }
}
