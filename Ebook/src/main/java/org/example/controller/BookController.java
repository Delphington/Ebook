package org.example.controller;

import org.example.ConstantsPath;
import org.example.UtilsInput;
import org.example.model.book.Book;
import org.example.model.book.BookManager;
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
                Long id = getIdChooseBook(bookManager.getMapBooks());
                Book book = bookManager.getMapBooks().get(id);
                manager.changeAndAddBookStatus(book);
            }
            case 2 -> {
                Long id = getIdChooseBook(bookManager.getMapBooks());
                bookManager.deleteBook(id);
            }
            case 3 -> printMenuListBook();

            case 4 -> bookManager.exportModel(getIdChooseBook(bookManager.getMapBooks()), ConstantsPath.EXPORT_FILE_BOOK,
                    bookManager.updateListBook());
            case 5 -> bookManager.exportAll();
            case 6 -> {
                bookManager.printAllFile(manager.IMPORT_FILE_BOOK);
                Long id = getScannerNumber();
                bookManager.importModel(id);
            }
            case 7 -> bookManager.importAll();


            case 8 -> actionType = ActionType.MAIN_MENU;
            case 9 -> actionType = ActionType.EXIT;
            default -> bookMenu.showErrorInput();

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
            case 2 -> bookMenu.printListObject(bookManager.sortByDatePublished(bookManager.updateListBook()));
            case 3 -> bookMenu.printListObject(bookManager.sorByPrice(bookManager.updateListBook()));
            case 4 -> bookMenu.printListObject(bookManager.sortByAmount(bookManager.updateListBook()));
            case 5 ->
                    bookMenu.printListObject(bookManager.sortByDeliverDate(bookManager.getStaleBook(bookManager.updateListBook())));
            case 6 ->
                    bookMenu.printListObject(bookManager.sorByPrice(bookManager.getStaleBook(bookManager.updateListBook())));
        }
    }
}