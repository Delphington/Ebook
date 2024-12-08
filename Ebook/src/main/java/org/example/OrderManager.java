package org.example;

import lombok.Getter;
import lombok.ToString;

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


    //Количество выполненных заказов
    public List<Order> getCompletedOrder(List<Order> orderList){
        return orderList.stream().filter(order -> order.getOrderStatusEnum()  == StatusOrderEnum.DONE)
                .collect(Collectors.toList());
    }

    //Выбор книг по статусу(приыбли не прибыли)
    public List<Order> getOrderListByStatus(StatusOrderEnum status){
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

    public List<Order> sortByDate(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getFinishDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Order> sortByStatus(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderStatusEnum).reversed())
                .collect(Collectors.toList());
    }

}
