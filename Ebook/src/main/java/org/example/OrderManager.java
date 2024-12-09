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

    //Обновляем во всех заказах добавление новой книги
    public void updateOrderList(Book book) {
        for (Order item : orderList) {
            List<RequestBook> requestBookList = item.getRequestBookList();
            for (RequestBook requestItem : requestBookList) {
                if (requestItem.getBook().equals(book)) {
                    requestItem.setRequestBookStatus(RequestBookStatus.CLOSED);
                }
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
                List<RequestBook> requestBookList = item.getRequestBookList();
                boolean flag = true;
                for (RequestBook requestItem : requestBookList) {
                    if (requestItem.getRequestBookStatus() == RequestBookStatus.OPEN) {
                        flag = false;
                    }
                }
                if (flag) {
                    order.completedOrder();
                    System.out.println("Заказ завершен!");
                } else {
                    System.out.println("# В заказе есть не книги недоступные");

                }
                ;
            }
        }
        System.out.println("Не найден заказ!");

    }

    public List<RequestBook> getListRequestBooks(List<Order> orders) {
        List<RequestBook> requestBookList = new ArrayList<>();
        for (Order item : orders) {
            List<RequestBook> temp = item.getRequestBookList();
            for (RequestBook itemTemp : temp) {
                if (itemTemp.getRequestBookStatus() == RequestBookStatus.OPEN) {
                    requestBookList.add(itemTemp);
                }
            }
        }
        return requestBookList;
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
