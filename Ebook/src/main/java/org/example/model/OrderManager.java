package org.example.model;

import lombok.Getter;
import lombok.ToString;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@ToString
@Getter
public class OrderManager {

    private List<Order> orderList;
    private RequestBookManager requestBookManager;
    private static final PrintStream printStream = System.out;
    private static final Scanner scanner = new Scanner(System.in);

    public OrderManager(List<Order> orderList) {
        this.orderList = orderList;

    }


    //Обновляем реквесты добавление новой книги
    public boolean updateRequestList(Book book) {
        for (RequestBook requestBook : RequestBook.requestBookList) {
            if (requestBook.getBook().equalsBook(book)) {
                requestBook.setRequestBookStatus(RequestBookStatus.CLOSED);
                book.decrementReferences();
                System.out.println("Был изменен одина книга");
                updateOrderList();
                return true;
            }
        }
        return false;
    }


    public void updateOrderList() {
        for (Order item : orderList) {
            int sizeList = item.getBookListInOrder().size();
            int cnt = 0;
            for (Book bookItem : item.getBookListInOrder()) {
                if (bookItem.getAmount() > 0) {
                    cnt++;
                }
            }
            if (cnt == sizeList) {
                item.setOrderStatusEnum(StatusOrderEnum.DONE);
            }
        }
    }


    public OrderManager(RequestBookManager requestBookManager) {
        orderList = new ArrayList<>();
        this.requestBookManager = requestBookManager;
    }

    public void createOrder() {
        orderList.add(new Order());
        System.out.println("### Заказ создан!");
    }

    public void cancelOrder(Order order) {
        if (order.getOrderStatusEnum() == StatusOrderEnum.DONE) {
            System.out.println("Заказ завершен, отменить невозможно!");
        } else {
            order.setOrderStatusEnum(StatusOrderEnum.CANCEL);
            //Закрываем все запросы на книги
            for (Book book : order.getBookListInOrder()) {
                if (book.getAmount() == 0) {
                    requestBookManager.closeRequest(book);
                }
            }
        }


        for (Order item : orderList) {
            if (item.equals(order)) {
                order.cancelOrder();
                System.out.println("Заказ отменент!");
                return;
            }
        }
        System.out.println("Не найден заказ!");
    }


    public void completedOrder(Order order) {
        for (Order item : orderList) {
            if (item.equals(order)) {
                item.completedOrder();
                return;
            }
        }
        System.out.println("Не найден заказ!");
    }


    //Меняем статус книги
    public void changeStatusOrder(Order order, StatusOrderEnum statusOrderEnum) {
        if (order.getOrderStatusEnum() == statusOrderEnum
                || statusOrderEnum == StatusOrderEnum.NEW) {
            System.out.println("### Такой статус не имеет смысла присваивать");
            return;
        }
        //Книга в статусе new - то есть книга в заказ
        //и присваиваем Done cancel
        if (order.getOrderStatusEnum() == StatusOrderEnum.NEW) {
            //Закрываем все запросы
            for (Book itemBook : order.getBookListInOrder()) {
                if (itemBook.getAmount() == 0) {
                    requestBookManager.closeRequest(itemBook);
                }
            }
        }
        //Это для любого статусу
        order.setOrderStatusEnum(statusOrderEnum);
        System.out.println("### Cтатус установленн");
    }


    public Integer getCompletedOrderByPrice(LocalDate from, LocalDate to) {
        int x = 0;
        for (Order order : orderList) {
            if ((order.getOrderStatusEnum() == StatusOrderEnum.DONE)
                    && (order.getCompletedDate().isAfter(from))
                    && (order.getCompletedDate().isBefore(to))) {
                x++;
            }
        }
        return x;
    }


    public Integer getSelectedOrderIndex() {
        printStream.println("Выбирите какой по счету заказ: ");
        if (orderList.size() == 0) {
            printStream.println("### нет заказов");
        }

        for (int i = 0; i < orderList.size(); i++) {
            printStream.println("[" + (i + 1) + "] " + orderList.get(i));
        }


        Integer number;
        while (true) {
            try {
                printStream.print("Введите число: ");
                String line = scanner.nextLine().trim();
                number = Integer.parseInt(line) - 1;
                if (number >= 0 && orderList.size() > number) {
                    break;
                }
                printStream.println("Неверный ввод! Попробуйте еще раз!");
            } catch (IllegalArgumentException e) {
                printStream.println("Неверный ввод! Попробуйте еще раз!");
            }
        }
        return number;
    }


    //Количество выполненных заказов
    public List<Order> getCompletedOrder(List<Order> orderList) {
        return orderList.stream().filter(order -> order.getOrderStatusEnum() == StatusOrderEnum.DONE)
                .collect(Collectors.toList());
    }

    //Выбор книг по статусу(приыбли не прибыли)
    public List<Order> getOrderListByStatus(StatusOrderEnum status) {
        return orderList.stream().filter(order -> order.getOrderStatusEnum().equals(status))
                .collect(Collectors.toList());
    }


    public List<Order> sortByAmount(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getAmountSum).reversed())
                .collect(Collectors.toList());

    }

    public List<Order> sortByCreateDate(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreateDate).reversed())
                .collect(Collectors.toList());
    }


    public List<Order> sortByCompletedDate(List<Order> orders) {
        return orders.stream()
                .filter(i -> i.getOrderStatusEnum() == StatusOrderEnum.DONE) //Filter for DONE status
                .sorted(Comparator.comparing(Order::getCreateDate).reversed())
                .collect(Collectors.toList());
    }


    public List<Order> sortByStatus(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderStatusEnum).reversed())
                .collect(Collectors.toList());
    }

}
