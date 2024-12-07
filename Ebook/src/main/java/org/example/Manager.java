package org.example;

public class Manager {
    private BookManager bookManager;
    private OrderManager orderManager;

    public Manager() {
        bookManager = new BookManager();
        orderManager = new OrderManager();
    }


    public void CreateOrder(){
        orderManager.createOrder();
    }



}
