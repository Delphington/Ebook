package org.example;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class OrderManager {

    @Getter
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

}
