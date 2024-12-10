package org.example.controller;

import org.example.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;


public class OrderController implements Controller {
    private OrderManager orderManager;
    private BookManager bookManager;
    private Manager manager;
    private RequestBookManager requestBookManager;

    public OrderController(Manager manager) {
        this.manager = manager;
        this.orderManager = manager.getOrderManager();
        this.bookManager = manager.getBookManager();
        this.requestBookManager = manager.getRequestBookManager();
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
            case 2 -> { //отменить заказа
                Integer indexOrder = getIndexChooseOrder();
                orderManager.cancelOrder(orderManager.getOrderList().get(indexOrder));
            }
            //отмена заказа
            case 3 -> {
                Integer indexBook = getIndexChooseBook(bookManager.getListBook());
                Integer indexOrder = getIndexChooseOrder();
                Order orderTemp = orderManager.getOrderList().get(indexOrder);
                orderTemp.addBook(bookManager.getListBook().get(indexBook));
            }

            case 4 -> printList(orderManager.sortByAmount(orderManager.getOrderList()));

            //printMenuListBook();
            case 5 ->    requestBookManager.printRequestBook();

            case 6 -> {
                Integer indexOrder = getIndexChooseOrder();
                Order orderTemp = orderManager.getOrderList().get(indexOrder);
                orderManager.changeStatusOrder(orderTemp, getChouseStatusOrder());
            }

            case 7 -> {
                LocalDate localDate1 = inputDate();
                LocalDate localDate2 = inputDate();
                orderManager.getCompletedOrderByPrice(localDate1, localDate2);

            }

            case 14 -> actionType = ActionType.MAIN_MENU;
            case 15 -> actionType = ActionType.EXIT;

            default -> {
                actionType = ActionType.BOOK_MENU;
                printStream.println("Неверный выбор! Попробуйте еще раз");
            }
        }
        return actionType;
    }



    private Integer getIndexChooseOrder() {
        printStream.println("Выбирите какой по счету заказ: ");
        if (orderManager.getOrderList().size() == 0) {
            printStream.println("### нет заказов");
        }

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
        printStream.println("[2] Отменить заказ"); //отменить
        printStream.println("[3] Добавить книгу в заказ");
        printStream.println("[4] Вывести все заказы");
        printStream.println("[5] Вывести книги, на которых запрос");
        printStream.println("[6] Изменить статус заказа");
        //---
        printStream.println("[7] Вывести сумму заработанных средств за период времени");
        printStream.println("[14] Вернуться в главное меню");
        printStream.println("[15] Выйти из программы");
        printStream.print("Введите число: ");
    }


    private void printMenuListBook() {
        printStream.println("[1] Вывести список заказов (сортировка по дате исполнения)");
        printStream.println("[2] Вывести список заказов (сортировка по цене)");
        printStream.println("[3] Вывести список заказов (сортировка по статусу)");
        printStream.println("[4] Вывести список выполненных заказов за период времени(сортировка по дате)");
        printStream.println("[5] Вывести список выполненных заказов за период времени(сортировка по цене)");
        printStream.println("[6] Вывести количество выполненных заказов за период времени");

        int choseAction;
        while (true) {
            choseAction = parseStringToInteger();
            if (choseAction >= 1 && choseAction <= 6) {
                break;
            }
            printStream.println("Попробуйте еще раз, это не верно!");
        }

        switch (choseAction) {
            case 2 -> printList(orderManager.sortByAmount(orderManager.getOrderList()));
        }
    }

    private void printList(List<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            printStream.println("{" + (i + 1) + "} " + orders.get(i));
        }
    }

    private StatusOrderEnum getChouseStatusOrder() {
        StatusOrderEnum statusOrderEnum;
        while (true) {
            System.out.println("Выберите новый статус");
            System.out.println("[1] NEW");
            System.out.println("[2] DONE");
            System.out.println("[3] CANCEL");
            int x;
            try {
                x = Integer.parseInt(scanner.nextLine().trim());
                statusOrderEnum = StatusOrderEnum.fromValue(x);
                break;
            } catch (RuntimeException e) {
                printStream.println("Невернный ввод!");
            }
        }
        return statusOrderEnum;
    }


    private LocalDate inputDate() {
        int year;
        int month;
        int day;
        while (true) {
            printStream.print("Введите год: ");
            try {
                year = Integer.parseInt(scanner.nextLine().trim());
                if (year > 0 && year < 3000) {
                    break;
                }
                printStream.println("Ошибка, не попали в верный промежуток!");
            } catch (RuntimeException e) {
                printStream.println("Ошибка, попробуйте еще раз!");
            }
        }

        while (true) {
            printStream.print("Введите месяц: ");
            try {
                month = Integer.parseInt(scanner.nextLine().trim());
                if (month > 0 && month < 13) {
                    break;
                }
                printStream.println("Ошибка, не попали в верный промежуток!");
            } catch (RuntimeException e) {
                printStream.println("Ошибка, попробуйте еще раз!");
            }
        }
        while (true) {
            printStream.print("Введите день: ");
            try {
                day = Integer.parseInt(scanner.nextLine().trim());
                if (day > 0 && day < 32) {
                    break;
                }
                printStream.println("Ошибка, не попали в верный промежуток!");
            } catch (RuntimeException e) {
                printStream.println("Ошибка, попробуйте еще раз!");
            }
        }
        return LocalDate.of(year, month, day);
    }


}


//Создать заказ
//2. Отменить заказ
//3. Посмотреть детали заказа
//4. Изменить статус заказа
//10. Вывести количество выполненных заказов за период времени
//11. Вывести сумму заработанных средств за период времени
//12. Вернуться в главное меню
//13. Выйти из программы
