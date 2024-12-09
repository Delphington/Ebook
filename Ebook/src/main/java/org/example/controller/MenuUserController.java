package org.example.controller;


import lombok.extern.log4j.Log4j2;
import org.example.Manager;

@Log4j2
public class MenuUserController implements Controller {

    private OrderController orderController;
    private BookController bookController;
    private Manager manager;


    public MenuUserController(Manager manager) {
        this.manager = manager;
        bookController = new BookController(manager.getBookManager(), manager);
        orderController = new OrderController(manager.getOrderManager(), manager.getBookManager(), manager);

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
        ActionType actionType;

        String nextLine = scanner.nextLine().trim();
        int temp;
        try {
            temp = Integer.parseInt(nextLine);
        } catch (IllegalArgumentException e) {
            log.warn("Невернный выбор главное меню");
            printStream.println("Неверный выбор! Попробуйте еще раз");
            return ActionType.MAIN_MENU;
        }


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
        System.out.println("==============================");
        System.out.println("=======    Main menu   =======");
        System.out.println("==============================");
        System.out.println("[1] Работа с книгами");
        System.out.println("[2] Работа с заказами");
        System.out.print("Введите число: ");

    }
}
