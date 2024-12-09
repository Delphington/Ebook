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

        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", LocalDate.of(1954, 7, 29), "Epic fantasy", 25.99, StatusBookEnum.AVAILABLE));
        books.add(new Book("Pride and Prejudice", "Jane Austen", LocalDate.of(1813, 1, 28), "Romance", 12.99, StatusBookEnum.AVAILABLE));
        books.add(new Book("1984", "George Orwell", LocalDate.of(1949, 6, 8), "Dystopian fiction", 10.99, StatusBookEnum.NOT_AVAILABLE));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", LocalDate.of(1960, 7, 11), "Southern Gothic", 15.99, StatusBookEnum.NOT_AVAILABLE));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", LocalDate.of(1925, 4, 10), "Classic American", 18.99, StatusBookEnum.AVAILABLE));

        BookManager bookManager = new BookManager(books);
        OrderManager orderManager = new OrderManager();
        Manager manager = new Manager(bookManager, orderManager);

        MenuUserController menuUserController = new MenuUserController(manager);
        menuUserController.run();
    }
}
