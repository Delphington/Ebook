package org.example.controller;

import org.example.model.Book;
import org.example.model.BookManager;
import org.example.model.Manager;
import org.example.view.BookMenu;

import java.util.List;

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

        int choseAction = parseStringToInteger();

        switch (choseAction) {
            case 1 -> {
                Integer indexBook = getIndexChooseBook(bookManager.getListBook());
              //  printList(bookManager.sortByName(bookManager.getListBook()));

                Book book = bookManager.getListBook().get(indexBook);
                manager.changeAndAddBookStatus(book);
            }
            case 2 -> {
                printList(bookManager.sortByName(bookManager.getListBook()));
                bookManager.deleteBook();
            }
            case 3 -> printMenuListBook();
            case 4 -> actionType = ActionType.MAIN_MENU;
            case 5 -> actionType = ActionType.EXIT;
            default -> {
                actionType = ActionType.BOOK_MENU;
                printStream.println("Неверный выбор! Попробуйте еще раз");
            }
        }
        return actionType;
    }


    private void printMenuListBook() {
        printStream.println("[1] Вывести список книг библиотеки (сортировка по алфавиту)");
        printStream.println("[2] Вывести список книг библиотеки (сортировка по дате издания)");
        printStream.println("[3] Вывести список книг библиотеки (сортировка по цене)");
        printStream.println("[4] Вывести список книг библиотеки (сортировка по статусу)");
        printStream.println("[5] Вывести список залежавшихся книг (сортировка по дате поступления)");
        int choseAction;
        while (true) {
            choseAction = parseStringToInteger();
            if (choseAction >= 1 && choseAction <= 6) {
                break;
            }
            printStream.println("Попробуйте еще раз, это не верно!");
        }

        switch (choseAction) {
            case 1 -> printList(bookManager.sortByName(bookManager.getListBook()));
            case 2 -> printList(bookManager.sortByDate(bookManager.getListBook()));
            case 3 -> printList(bookManager.sorByPrice(bookManager.getListBook()));
            case 4 -> printList(bookManager.sortByStatus(bookManager.getListBook()));
            case 5 -> printList(bookManager.getStaleBook(bookManager.getListBook()));
        }
    }


    private void printList(List<Book> book) {
        for (int i = 0; i < book.size(); i++) {
            printStream.println("{" + (i + 1) + "} " + book.get(i));
        }
    }

}
