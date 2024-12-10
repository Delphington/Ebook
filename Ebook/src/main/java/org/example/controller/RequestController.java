package org.example.controller;

import org.example.model.Book;
import org.example.model.Manager;
import org.example.view.RequestBookMenu;

import java.util.List;

public class RequestController implements Controller {

    private Manager manager;
    private RequestBookMenu requestBookMenu;

    public RequestController(Manager manager) {
        this.manager = manager;
        requestBookMenu = new RequestBookMenu();
    }

    @Override
    public ActionType run() {
        while (true) {
            requestBookMenu.showMenu();
            ActionType actionType = input();
            if (actionType != ActionType.REQUEST_MENU) {
                break;
            }
        }
        return ActionType.MAIN_MENU;
    }


    @Override
    public ActionType input() {
        ActionType actionType = ActionType.REQUEST_MENU;
        int temp = parseStringToInteger();
        switch (temp) {
            case 1 -> {
                Integer indexBook = getIndexChooseBook(manager.getBookManager().getListBook());
                manager.getRequestBookManager().createRequestBook(manager.getBookManager().getListBook().get(indexBook));
            }
            case 2 -> requestBookMenu.printListObject(manager.getRequestBookManager().getBookRequestSortedReference());
            case 3 -> printStream.println();
            case 4 -> actionType = ActionType.MAIN_MENU;
            case 5 -> actionType = ActionType.EXIT;
            default -> {
                actionType = ActionType.MAIN_MENU;
                requestBookMenu.showErrorInput();
            }
        }
        return actionType;
    }


}
