package org.example.controller;


import lombok.extern.log4j.Log4j2;
import org.example.CellBookManager;


@Log4j2
public class BookController implements Controller {
    private CellBookManager cellBookManager;

    public BookController(CellBookManager cellBookManager) {
        this.cellBookManager = cellBookManager;
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

        String nextLine = scanner.nextLine().trim();
        int temp;
        try {
            temp = Integer.parseInt(nextLine);
        } catch (IllegalArgumentException e) {
            log.warn("Невернный выбор в book меню");
            printStream.println("Неверный выбор! Попробуйте еще раз");
            return ActionType.BOOK_MENU;
        }


        switch (temp) {
            case 1 -> {
                cellBookManager.addBook();
            }
            case 2 -> {
                cellBookManager.deleteBook();
            }
            case 3 -> {
                cellBookManager.changeStatusBook();
            }

            case 4 -> {
                printStream.println(cellBookManager.sortByName(cellBookManager.getListBook()));
            }
            case 5 -> {
                printStream.println(cellBookManager.sortByDate(cellBookManager.getListBook()));
            }
            case 6 -> {
                printStream.println(cellBookManager.sorByPrice(cellBookManager.getListBook()));
            }
            case 7 -> {
                printStream.println(cellBookManager.sortByStatus(cellBookManager.getListBook()));
            }
            case 8 -> {
                //Список залежвашихся книг

                //List<CellBook> arr = cellBookManager.getStaleBook(cellBo)

                //todo: надо подумать как разулить с датами
                // printStream.println(cellBookManager.sortByDate(cellBookManager.getStaleBook(cellBookManager.getListBook())));
            }
            case 9 -> {
                // printStream.println(cellBookManager.sorByPrice(cellBookManager.getStaleBook(cellBookManager.getListBook())));
            }
            case 10 -> {
                actionType = ActionType.MAIN_MENU;
            }
            case 11 -> {
                actionType = ActionType.EXIT;
            }
            default -> {
                actionType = ActionType.BOOK_MENU;
                printStream.println("Неверный выбор! Попробуйте еще раз");
            }
        }
        return actionType;
    }

    @Override
    public void showMenu() {
        printStream.println("[1] Добавить книгу на склад");
        printStream.println("[2] Списать книгу со склада");
        printStream.println("[3] изменить статус книги");
        printStream.println("[4] Вывести список книг библиотеки (сортировка по алфавиту)");
        printStream.println("[5] Вывести список книг библиотеки (сортировка по дате издания)");
        printStream.println("[6] Вывести список книг библиотеки (сортировка по цене)");
        printStream.println("[7] Вывести список книг библиотеки (сортировка по наличию на складе)");
        printStream.println("[8] Вывести список залежавшихся книг (сортировка по дате поступления)");
        printStream.println("[9] Вывести список залежавшихся книг (сортировка по цене)");
        printStream.println("[10] Вернуться в главное меню");
        printStream.println("[11] Выйти из программы");
    }

}
