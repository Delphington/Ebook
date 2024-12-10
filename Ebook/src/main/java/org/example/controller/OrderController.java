package org.example.controller;

import org.example.*;

import java.util.List;

public class OrderController implements Controller {
    private OrderManager orderManager;
    private BookManager bookManager;
    private Manager manager;

    public OrderController(Manager manager) {
        this.manager = manager;
        this.orderManager = manager.getOrderManager();
        this.bookManager = manager.getBookManager();
    }


    @Override
    public ActionType run() {
        while (true) {
            ActionType actionType = input();
            if (actionType != ActionType.ORDER_MENU) {
                return actionType;
            }
        }
    }

    @Override
    public ActionType input() {
        showMenu();
        String nextLine = scanner.nextLine().trim();
        int temp;
        try {
            temp = Integer.parseInt(nextLine);
        } catch (IllegalArgumentException e) {
            printStream.println("Неверный выбор! Попробуйте еще раз");
            return ActionType.ORDER_MENU;
        }

        ActionType actionType = ActionType.ORDER_MENU;

        switch (temp) {
            case 1 -> orderManager.createOrder();

            case 2 -> { //завершение заказа
                Integer index = getIndexChooseOrder();
                orderManager.completedOrder(orderManager.getOrderList().get(index));
            }
            //отмена заказа
            case 3 -> {
                Integer index = getIndexChooseOrder();
                orderManager.cancelOrder(orderManager.getOrderList().get(index));
            }
            //добавление книги
            case 4 -> {
                Integer indexOrder = getIndexChooseOrder();
                Integer indexBook = getIndexChooseBook();
                Order orderTemp = orderManager.getOrderList().get(indexOrder);
                orderTemp.addBook(bookManager.getListBook().get(indexBook));
            }

            case 5 -> printStream.println(bookManager.getListBook());
            case 6 -> {
//                List<RequestBook> requestBookList = orderManager.getListRequestBooks(orderManager.getOrderList());
//                for (RequestBook item : requestBookList) {
//                    printStream.println(item.getBook());
//                }
            }

            case 7 -> manager.changeAndAddBookStatus();
            case 8 -> printStream.println(orderManager.sortByAmount(orderManager.getOrderList()));
            case 9 -> printStream.println(orderManager.sortByStatus(orderManager.getOrderList()));
            case 14 -> actionType = ActionType.MAIN_MENU;
            case 15 -> actionType = ActionType.EXIT;

            default -> {
                actionType = ActionType.BOOK_MENU;
                printStream.println("Неверный выбор! Попробуйте еще раз");
            }
        }
        return actionType;
    }


    public Integer getIndexChooseBook() {
        printStream.println("Выбирите какой по счету книгу для заказа: ");
        for (int i = 0; i < bookManager.getListBook().size(); i++) {
            printStream.println("[" + (i + 1) + "] " + bookManager.getListBook().get(i));
        }

        Integer number;
        while (true) {
            try {
                printStream.print("Введите число: ");
                String line = scanner.nextLine().trim();
                number = Integer.parseInt(line) - 1;
                if (number >= 0 && bookManager.getListBook().size() > number) {
                    break;
                }
                printStream.println("Неверно! Попробуй еще раз ввести число: ");
            } catch (IllegalArgumentException e) {
                printStream.println("Попробуй еще раз ввести число: ");
            }
        }
        return number;
    }


    private Integer getIndexChooseOrder() {
        printStream.println("Выбирите какой по счету заказ: ");
        for (int i = 0; i < orderManager.getOrderList().size(); i++) {
            printStream.println("[" + (i + 1) + "] " + orderManager.getOrderList().get(i));
        }

        Integer number;
        while (true) {
            try {
                printStream.print("Введите число: ");
                String line = scanner.nextLine().trim();
                number = Integer.parseInt(line) - 1;
                if (number >= 0 && orderManager.getOrderList().size() > number) {
                    break;
                }
                printStream.println("Неверно! Попробуй еще раз ввести число: ");
            } catch (IllegalArgumentException e) {
                printStream.println("Попробуй еще раз ввести число: ");
            }
        }
        return number;
    }


    @Override
    public void showMenu() {
        printStream.println("Выбирите действие с заказами: ");
        printStream.println("[1] Создать заказ");
        printStream.println("[2] Завершить заказ"); //отменить
        printStream.println("[3] Отменить заказ"); //отменить
        printStream.println("[4] Добавить книгу в заказ");
        printStream.println("[5] Вывести все книги");
        printStream.println("[6] Вывести книги, на которых запрос");

        printStream.println("[7] Сменить статус книги");

        printStream.println("[7] Вывести список заказов (сортировка по дате исполнения)");
        printStream.println("[8] Вывести список заказов (сортировка по цене)");
        printStream.println("[9] Вывести список заказов (сортировка по статусу)");
        printStream.println("[10] Вывести список выполненных заказов за период времени(сортировка по дате)");
        printStream.println("[11] Вывести список выполненных заказов за период времени(сортировка по цене)");
        printStream.println("[12] Вывести количество выполненных заказов за период времени");
        printStream.println("[13] Вывести сумму заработанных средств за период времени");
        printStream.println("[14] Вернуться в главное меню");
        printStream.println("[15] Выйти из программы");
        printStream.print("Введите число: ");
    }

}
