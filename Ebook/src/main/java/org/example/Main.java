package org.example;

//package org.example;
//
//
//import java.io.PrintStream;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//

import org.example.controller.MenuUserController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // private static final PrintStream printStream =  System.out;

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();

        books.add(new Book("1A", "1B", LocalDate.of(1954, 7, 29), "1", 101.0, 0));
        books.add(new Book("2A", "2B", LocalDate.of(1813, 1, 28), "2", 102.0, 1));
        books.add(new Book("3A", "3B", LocalDate.of(1949, 6, 8), "3", 103.0, 2));
        books.add(new Book("4A", "4B", LocalDate.of(1960, 7, 11), "4", 104.0, 0));
        books.add(new Book("5A", "5B", LocalDate.of(1925, 4, 10), "5", 105.0, 1));

        BookManager bookManager = new BookManager(books);
        RequestBookManager requestBookManager = new RequestBookManager();
        OrderManager orderManager = new OrderManager(requestBookManager);
        Manager manager = new Manager(bookManager, orderManager,requestBookManager);

        MenuUserController menuUserController = new MenuUserController(manager);
        menuUserController.run();
    }
}


//Мы не может не добалять книги не удалять
