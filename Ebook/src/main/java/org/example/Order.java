package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Order {
    @Getter
    @Setter
    private StatusOrderEnum orderStatusEnum;
    private List<Book> bookListInOrder;


    public Order() {
        bookListInOrder = new ArrayList<>();

    }

    public void addBook(Book book){
        bookListInOrder.add(book);
    }

    public void deleteBook(Book book){
        bookListInOrder.remove(book);
    }


}
