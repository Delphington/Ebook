package org.example;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public class OrderManager {

    private List<Order> orderList;

    public OrderManager() {
        orderList = new ArrayList<>();
        orderList.add(new Order());
        orderList.add(new Order());

    }

    public void createOrder(){
        orderList.add(new Order());
    }


    public void cancelOrder(){

    }

    public List<Order> sortByAmount(){
        return orderList.stream()
                .sorted(Comparator.comparing(Order::getAmountSum).reversed())
                .collect(Collectors.toList());

    }

    public List<Order>  sortByDate(){
        return orderList.stream()
                .sorted(Comparator.comparing(Order::getFinishDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Order>  sortByStatus(){
        return orderList.stream()
                .sorted(Comparator.comparing(Order::getOrderStatusEnum).reversed())
                .collect(Collectors.toList());
    }

}
