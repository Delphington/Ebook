package org.example;

//package org.example;
//
//
//import java.io.PrintStream;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//

import org.example.controller.BookController;
import org.example.controller.MenuUserController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // private static final PrintStream printStream =  System.out;

    public static void main(String[] args) {

        List<CellBook> cellBooks = new ArrayList<>();

        cellBooks.add(new CellBook("The Lord of the Rings", "J.R.R. Tolkien", LocalDate.of(1954, 7, 29), "Epic fantasy", 25.99, StatusBookEnum.AVAILABLE));
        cellBooks.add(new CellBook("Pride and Prejudice", "Jane Austen", LocalDate.of(1813, 1, 28), "Romance", 12.99, StatusBookEnum.AVAILABLE));
        cellBooks.add(new CellBook("1984", "George Orwell", LocalDate.of(1949, 6, 8), "Dystopian fiction", 10.99, StatusBookEnum.NOT_AVAILABLE));
        cellBooks.add(new CellBook("To Kill a Mockingbird", "Harper Lee", LocalDate.of(1960, 7, 11), "Southern Gothic", 15.99, StatusBookEnum.NOT_AVAILABLE));
        cellBooks.add(new CellBook("The Great Gatsby", "F. Scott Fitzgerald", LocalDate.of(1925, 4, 10), "Classic American", 18.99, StatusBookEnum.AVAILABLE));

        CellBookManager cellBookManager = new CellBookManager(cellBooks);
        OrderManager orderManager = new OrderManager();
        Manager manager = new Manager(cellBookManager, orderManager);

        MenuUserController menuUserController = new MenuUserController(manager);
        menuUserController.run();
    }
}
