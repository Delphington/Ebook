package org.example;

import com.sun.org.apache.xpath.internal.operations.Or;

public class Manager {
    private BookManager bookManager;
    private OrderManager orderManager;

    public Manager() {
        bookManager = new BookManager();
        orderManager = new OrderManager();
    }




}
