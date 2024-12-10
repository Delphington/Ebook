package org.example.controller;

import org.example.Book;
import org.example.Manager;
import org.example.RequestBook;

import java.util.List;

public class RequestController implements Controller {

    private Manager manager;

    public RequestController(Manager manager) {
        this.manager = manager;
    }

    @Override
    public ActionType run() {
        while (true) {
            showMenu();
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
                RequestBook.createRequestBook(manager.getBookManager().getListBook().get(indexBook));
            }
            case 2 -> printList(RequestBook.getBookRequestSortedReference());
            case 3 -> printStream.println();
            case 4 -> actionType = ActionType.MAIN_MENU;
            case 5 -> actionType = ActionType.EXIT;
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
        printStream.println("=======  Request menu  =======");
        printStream.println("==============================");
        printStream.println("[1] Оставить запрос на книгу");
        printStream.println("[2] Вывести список запросов на книги (сортировка по количеству запросов)");
        printStream.println("[3] Вывести список запросов на книги (сортировка по цене)");
        printStream.println("[4] Вернуться в главное меню");
        printStream.println("[5] Выйти из программы");
    }

    private void printList(List<Book> book) {
        for (int i = 0; i < book.size(); i++) {
            printStream.println("{" + (i + 1) + "} " + book.get(i) + ", reference = " +book.get(i).getReferences());
        }
    }
}
