package org.example;

public class Manager {
    private BookManager bookManager;
    private OrderManager orderManager;

    public Manager(BookManager bookManager, OrderManager orderManager) {
        this.bookManager = bookManager;
        this.orderManager = orderManager;
    }

    public void CreateOrder(){
        orderManager.createOrder();
    }



}
