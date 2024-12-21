package org.example.controller;

import org.example.UtilsInput;
import org.example.model.Manager;
import org.example.model.request.RequestBook;
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
                //TODO: ## ПЕПРОВЕРИТЬ Логику
                Long id = getIdChooseBook(manager.getBookManager().getMapBooks());
                requestBookManager.createRequestBook(manager.getBookManager().getMapBooks().get(id));
            }
            case 2 ->
                    requestBookMenu.printListObject(manager.getBookManager().sorByReference(requestBookManager.getBookFromRequestBook()));
            case 3 ->
                    requestBookMenu.printListObject(manager.getBookManager().sorByPrice(requestBookManager.getBookFromRequestBook()));
            case 4 -> {
                Integer index = getIndexChoose(RequestBook.requestBookList);
                requestBookManager.exportModel(RequestBook.requestBookList.get(index).getId());
            }
            case 5 -> requestBookManager.exportAll();

            case 6 -> {
                //todo: может здесь и другой метод залутать можно getBookId

                requestBookManager.printAllFile(manager.IMPORT_FILE_REQUEST_BOOK);
                Long id = getScannerNumber();
                requestBookManager.importModel(id);
            }
            case 7 -> {
                requestBookManager.importAll();
            }
            case 8 -> actionType = ActionType.MAIN_MENU;
            case 9 -> actionType = ActionType.EXIT;
            default -> requestBookMenu.showErrorInput();
        }
        return actionType;
    }
}
