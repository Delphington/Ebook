package org.example.controller;

import org.example.BookManager;
import org.example.Manager;

public class BookController implements Controller {
    private BookManager bookManager;
    private Manager manager;

    public BookController(Manager manager) {
        this.bookManager = manager.getBookManager();
        this.manager = manager;
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
        showMenu();
        ActionType actionType = ActionType.BOOK_MENU;

        int choseAction = parseStringToInteger();

        switch (choseAction) {
            case 1 -> manager.changeAndAddBookStatus();
            case 2 -> bookManager.deleteBook();
            case 3 -> printListBook();
            case 4 -> actionType = ActionType.MAIN_MENU;
            case 5 -> actionType = ActionType.EXIT;
            default -> {
                actionType = ActionType.BOOK_MENU;
                printStream.println("Неверный выбор! Попробуйте еще раз");
            }
        }
        return actionType;
    }

    //Смена статуса книи, То есть добавление существующий  из писка
    @Override
    public void showMenu() {
        printStream.println("[1] Добавить новую книгу на склад");
        printStream.println("[2] Списать книгу со склада");
        printStream.println("[3] Вывести список книг");
        printStream.println("[4] Вернуться в главное меню");
        printStream.println("[5] Выйти из программы");
    }

    private void printListBook() {
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
            case 1 -> printStream.println(bookManager.sortByName(bookManager.getListBook()));
            case 2 -> printStream.println(bookManager.sortByDate(bookManager.getListBook()));
            case 3 -> printStream.println(bookManager.sorByPrice(bookManager.getListBook()));
            case 4 -> printStream.println(bookManager.sortByStatus(bookManager.getListBook()));
            case 5 -> bookManager.getStaleBook(bookManager.getListBook());
        }
    }
}
