package org.example.controller;

import org.example.UtilsInput;
import org.example.model.Manager;
import org.example.model.request.RequestBookManager;
import org.example.view.RequestBookMenu;

public class RequestController implements Controller {

    private Manager manager;
    private RequestBookMenu requestBookMenu;
    private RequestBookManager requestBookManager;

    public RequestController(Manager manager) {
        this.manager = manager;
        this.requestBookManager = manager.getRequestBookManager();
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
        int temp = UtilsInput.getIntegerFromConsole();
        switch (temp) {
            case 1 -> {
                Integer indexBook = getIndexChooseBook(manager.getBookManager().getListBook());
                requestBookManager.createRequestBook(manager.getBookManager().getListBook().get(indexBook));
            }
            case 2 -> requestBookMenu.printListObject(manager.getBookManager().sorByReference(requestBookManager.getBookFromRequestBook()));
            case 3 -> requestBookMenu.printListObject(manager.getBookManager().sorByPrice(requestBookManager.getBookFromRequestBook()));
            case 4 -> actionType = ActionType.MAIN_MENU;
            case 5 -> actionType = ActionType.EXIT;
            default -> requestBookMenu.showErrorInput();
        }
        return actionType;
    }
}
