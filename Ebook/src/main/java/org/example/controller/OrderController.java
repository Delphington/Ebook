package org.example.controller;

import org.example.UtilsInput;
import org.example.model.*;
import org.example.view.OrderMenu;

import java.time.LocalDate;


public class OrderController implements Controller {
    private OrderManager orderManager;
    private BookManager bookManager;
    private Manager manager;
    private RequestBookManager requestBookManager;
    private OrderMenu orderMenu;

    public OrderController(Manager manager) {
        this.manager = manager;
        this.orderManager = manager.getOrderManager();
        this.bookManager = manager.getBookManager();
        this.requestBookManager = manager.getRequestBookManager();
        orderMenu = new OrderMenu();
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
        orderMenu.showMenu();
        String nextLine = scanner.nextLine().trim();
        int temp;
        try {
            temp = Integer.parseInt(nextLine);
        } catch (IllegalArgumentException e) {
            orderMenu.showErrorInput();
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

            case 4 ->  orderMenu.printListObject(orderManager.sortByAmount(orderManager.getOrderList()));

            //printMenuListBook();
            case 5 ->    requestBookManager.printRequestBook();

            case 6 -> {
                Integer indexOrder = getIndexChooseOrder();
                Order orderTemp = orderManager.getOrderList().get(indexOrder);
                orderManager.changeStatusOrder(orderTemp, getChouseStatusOrder());
            }

            case 7 -> {
                LocalDate localDate1 = UtilsInput.inputDateFromConsole();
                LocalDate localDate2 = UtilsInput.inputDateFromConsole();
                orderManager.getCompletedOrderByPrice(localDate1, localDate2);

            }

            case 14 -> actionType = ActionType.MAIN_MENU;
            case 15 -> actionType = ActionType.EXIT;

            default -> {
                actionType = ActionType.BOOK_MENU;
                orderMenu.showErrorInput();
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
                orderMenu.showErrorInput();
            } catch (IllegalArgumentException e) {
                orderMenu.showErrorInput();
            }
        }
        return number;
    }


    private void printMenuListBook() {
       orderMenu.showTypeInfoList();
        int choseAction;
        while (true) {
            choseAction = parseStringToInteger();
            if (choseAction >= 1 && choseAction <= 6) {
                break;
            }
            orderMenu.showErrorInput();
        }

        switch (choseAction) {
            case 2 -> orderMenu.printListObject(orderManager.sortByAmount(orderManager.getOrderList()));
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
                orderMenu.showErrorInput();
            }
        }
        return statusOrderEnum;
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
