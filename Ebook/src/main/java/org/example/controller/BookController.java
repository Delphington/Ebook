package org.example.controller;

import org.example.UtilsInput;
import org.example.model.Book;
import org.example.model.BookManager;
import org.example.model.Manager;
import org.example.view.BookMenu;


public class BookController implements Controller {
    private BookManager bookManager;
    private Manager manager;
    private BookMenu bookMenu;

    public BookController(Manager manager) {
        this.bookManager = manager.getBookManager();
        this.manager = manager;
        this.bookMenu = new BookMenu();
    }

    @Override
    public ActionType run() {
        while (true) {
            ActionType actionType = input();
            if (actionType != ActionType.BOOK_MENU) {
                return actionType;
            }
        }
    }

    @Override
    public ActionType input() {
        bookMenu.showMenu();
        ActionType actionType = ActionType.BOOK_MENU;

        int choseAction = UtilsInput.getIntegerFromConsole();

        switch (choseAction) {
            case 1 -> {
                Integer indexBook = getIndexChooseBook(bookManager.getListBook());
                Book book = bookManager.getListBook().get(indexBook);
                manager.changeAndAddBookStatus(book);
            }
            case 2 -> {
                bookMenu.printListObject(bookManager.sortByName(bookManager.getListBook()));
                bookManager.deleteBook();
            }
            case 3 -> printMenuListBook();
            case 4 -> actionType = ActionType.MAIN_MENU;
            case 5 -> actionType = ActionType.EXIT;
            default -> {
                actionType = ActionType.BOOK_MENU;
                bookMenu.showErrorInput();
            }
        }
        return actionType;
    }


    private void printMenuListBook() {
        bookMenu.showTypeInfoList();
        int choseAction;
        while (true) {
            choseAction = UtilsInput.getIntegerFromConsole();
            if (choseAction >= 1 && choseAction <= 6) {
                break;
            }
            bookMenu.showErrorInput();
        }

        switch (choseAction) {
            case 1 -> bookMenu.printListObject(bookManager.sortByName(bookManager.getListBook()));
            case 2 -> bookMenu.printListObject(bookManager.sortByDate(bookManager.getListBook()));
            case 3 -> bookMenu.printListObject(bookManager.sorByPrice(bookManager.getListBook()));
            case 4 -> bookMenu.printListObject(bookManager.sortByStatus(bookManager.getListBook()));
            case 5 -> bookMenu.printListObject(bookManager.getStaleBook(bookManager.getListBook()));
        }
    }
}