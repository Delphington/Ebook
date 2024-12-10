package org.example.controller;

import org.example.UtilsInput;
import org.example.model.Manager;
import org.example.view.MainMenu;

public class MenuUserController implements Controller {

    private RequestController requestController;
    private OrderController orderController;
    private BookController bookController;
    private Manager manager;
    private MainMenu mainMenu;

    public MenuUserController(Manager manager) {
        this.manager = manager;
        bookController = new BookController(manager);
        orderController = new OrderController(manager);
        requestController = new RequestController(manager);
        mainMenu = new MainMenu();
    }

    @Override
    public ActionType run() {
        while (true) {
            mainMenu.showMenu();
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
        int temp = UtilsInput.getIntegerFromConsole();
        switch (temp) {
            case 1 -> actionType = bookController.run();
            case 2 -> actionType = orderController.run();
            case 3 -> actionType = requestController.run();
            default -> {
                actionType = ActionType.MAIN_MENU;
                mainMenu.showErrorInput();
            }
        }
        return actionType;
    }

}
