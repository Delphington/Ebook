package org.example.controller;

import org.example.ConstantsPath;
import org.example.UtilsInput;
import org.example.model.*;
import org.example.model.book.BookManager;
import org.example.model.order.Order;
import org.example.model.order.OrderManager;
import org.example.model.order.StatusOrderEnum;
import org.example.model.request.RequestBookManager;
import org.example.view.OrderMenu;

import java.time.LocalDate;
import java.util.List;
//todo: клиент

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
                Integer indexOrder = orderManager.getSelectedOrderIndex();
                orderManager.cancelOrder(orderManager.getOrderList().get(indexOrder));
            }
            //отмена заказа
            case 3 -> {
                //TODO: ##
                Long indexBook = getIdChooseBook(bookManager.getMapBooks());
                Integer indexOrder = orderManager.getSelectedOrderIndex();
                Order orderTemp = orderManager.getOrderList().get(indexOrder);
                orderTemp.addBook(bookManager.getMapBooks().get(indexBook));
            }

            case 4 -> orderMenu.printListObject(orderManager.sortByAmount(orderManager.getOrderList()));

            //Вывести книги, на которых запрос
            case 5 -> requestBookManager.printRequestOpenBook(); //todo rename function

            case 6 -> {
                //Сменить статус
                Integer indexOrder = orderManager.getSelectedOrderIndex();
                Order orderTemp = orderManager.getOrderList().get(indexOrder);
                orderManager.changeStatusOrder(orderTemp, StatusOrderEnum.getChosenOrderStatus());
            }
            case 7 -> printStream.println(orderManager.getOrderList().get(orderManager.getSelectedOrderIndex()));
            case 8 -> printStream.println("Прибыль закрытый заказов: " + orderManager.calculateCompletedOrderProfit());
            case 9 -> printMenuListBook();

            case 10 -> orderManager.exportModel(getIdChoose(orderManager.getOrderList()),
                    ConstantsPath.EXPORT_FILE_ORDER, orderManager.getOrderList());

            case 11 -> {
                orderManager.exportAll();
            }
            case 12 -> {
                orderManager.printAllFile(manager.IMPORT_FILE_ORDER);
                Long id = getScannerNumber();
                orderManager.importModel(id);


            }
            case 13 -> orderManager.importAll();


            case 14 -> actionType = ActionType.MAIN_MENU;
            case 15 -> actionType = ActionType.EXIT;

            default -> {
                actionType = ActionType.BOOK_MENU;
                orderMenu.showErrorInput();
            }
        }
        return actionType;
    }


    private void printMenuListBook() {
        orderMenu.showTypeInfoList();
        int choseAction;
        while (true) {
            choseAction = UtilsInput.getIntegerFromConsole();
            if (choseAction >= 1 && choseAction <= 6) {
                break;
            }
            orderMenu.showErrorInput();
        }

        switch (choseAction) {
            case 1 -> orderMenu.printListObject(orderManager.sortByCompletedDate(orderManager.getOrderList()));
            case 2 -> orderMenu.printListObject(orderManager.sortByAmount(orderManager.getOrderList()));
            case 3 -> orderMenu.printListObject(orderManager.sortByStatus(orderManager.getOrderList()));
            case 4 -> {
                LocalDate localDate1 = UtilsInput.inputDateFromConsole();
                LocalDate localDate2 = UtilsInput.inputDateFromConsole();
                orderMenu.printListObject(orderManager.sortByCompletedOrdersBetweenDates(orderManager.getOrderList(), localDate1, localDate2));
            }
            case 5 -> {
                LocalDate localDate1 = UtilsInput.inputDateFromConsole();
                LocalDate localDate2 = UtilsInput.inputDateFromConsole();
                List<Order> list = orderManager.sortByCompletedOrdersBetweenDates(orderManager.getOrderList(), localDate1, localDate2);
                orderMenu.printListObject(orderManager.sortByAmount(list));
            }
            case 6 -> {
                LocalDate localDate1 = UtilsInput.inputDateFromConsole();
                LocalDate localDate2 = UtilsInput.inputDateFromConsole();
                List<Order> list = orderManager.sortByCompletedOrdersBetweenDates(orderManager.getOrderList(), localDate1, localDate2);
                printStream.println("Количество: " + list.size());
            }
        }
    }
}
