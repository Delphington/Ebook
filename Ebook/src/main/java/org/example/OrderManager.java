package org.example;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public class OrderManager {

    private List<Order> orderList;

    public OrderManager(List<Order> orderList) {
        this.orderList = orderList;

    }


    //Обновляем реквесты добавление новой книги
    public boolean updateRequestList(Book book) {
        for (RequestBook requestBook : RequestBook.requestBookList) {
            if (requestBook.getBook().equalsBook(book)) {
                requestBook.setRequestBookStatus(RequestBookStatus.CLOSED);
                book.incrementAmount();
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


    public OrderManager() {
        orderList = new ArrayList<>();
    }

    public void createOrder() {
        orderList.add(new Order());
        System.out.println("## Заказ создан!");
    }

    public void cancelOrder(Order order) {
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
//
//    public List<RequestBook> getListRequestBooks(List<Order> orders) {
//        List<RequestBook> requestBookList = new ArrayList<>();
//        for (Order item : orders) {
//            List<RequestBook> temp = item.getRequestBookList();
//            for (RequestBook itemTemp : temp) {
//                if (itemTemp.getRequestBookStatus() == RequestBookStatus.OPEN) {
//                    requestBookList.add(itemTemp);
//                }
//            }
//        }
//        return requestBookList;
//    }


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

    //Меняем статус книги
//    public void changeStatusOrder(Order order, StatusOrderEnum statusOrderEnum){
//        order.setOrderStatusEnum(statusOrderEnum);
//    }


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
