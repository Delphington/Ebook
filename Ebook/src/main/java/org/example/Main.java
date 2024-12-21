package org.example;


import lombok.experimental.UtilityClass;
import org.example.controller.MenuUserController;
import org.example.model.*;
import org.example.model.book.Book;
import org.example.model.book.BookManager;
import org.example.model.order.Order;
import org.example.model.order.OrderManager;
import org.example.model.request.RequestBook;
import org.example.model.request.RequestBookManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Main {

    public static void main(String[] args) {


        // 1. Создание отдельных переменных для каждого объекта Book:
        Book book1 = new Book("1A", "1B", LocalDate.of(1954, 7, 29), "1", 101.0, 0);
        Book book2 = new Book("2A", "2B", LocalDate.of(1813, 1, 28), "2", 102.0, 1);
        Book book3 = new Book("3A", "3B", LocalDate.of(1949, 6, 8), "3", 103.0, 2);
        Book book4 = new Book("4A", "4B", LocalDate.of(1960, 7, 11), "4", 104.0, 0);
        Book book5 = new Book("5A", "5B", LocalDate.of(1925, 4, 10), "5", 105.0, 1);

        // 2. Создание списка (List) и добавление в него объектов Book:
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);


        BookManager bookManager = new BookManager(books);
        RequestBookManager requestBookManager = new RequestBookManager();
        requestBookManager.setBookManager(bookManager);
        OrderManager orderManager = new OrderManager(requestBookManager, bookManager);
//        orderManager.createOrder();
//        orderManager.createOrder();
        Manager manager = new Manager(bookManager, orderManager, requestBookManager);

        MenuUserController menuUserController = new MenuUserController(manager);
        //menuUserController.run();


        //todo: на всех уровнях есть bookManager

        RequestBook r1 = new RequestBook(book1);
        RequestBook r2 = new RequestBook(book2);

        RequestBook.requestBookList = new ArrayList<>(List.of(r1, r2));

        for (RequestBook requestBook : RequestBook.requestBookList) {
            System.out.println(requestBook);
        }
        System.out.println("-----------------------------------");
        requestBookManager.readFromFile();
        for (RequestBook requestBook : RequestBook.requestBookList) {
            System.out.println(requestBook);
        }
//
//        manager.getOrderManager().createOrder();
//        manager.getOrderManager().createOrder();
//        manager.getOrderManager().createOrder();
//        manager.getOrderManager().getOrderList().get(0).addBook(books.get(0));
//        manager.getOrderManager().getOrderList().get(0).addBook(books.get(2));
//        manager.getOrderManager().getOrderList().get(1).addBook(books.get(1));


//        manager.getOrderManager().writeToFile();


        //Тесты для Заказов
//        System.out.println("==========Все книги=================");
//        for(Book book :  manager.getBookManager().getListBook()){
//            System.out.println(book);
//        }
//
//
//        for(Order order :  manager.getOrderManager().getOrderList()){
//            System.out.println(order);
//        }
//
//        System.out.println("-------------------------------------------");
//
//
//        manager.getOrderManager().readFromFile();
//
//
//        for(Order order :  manager.getOrderManager().getOrderList()){
//            System.out.println(order);
//        }


        //Тесты для книг
//        manager.getBookManager().writeToFile();
//
//        for(Book book : manager.getBookManager().getListBook()){
//            System.out.println(book);
//        }
//
//        System.out.println("--------------------");
//        manager.getBookManager().readFromFile();
//
//        for(Book book : manager.getBookManager().generateList()){
//            System.out.println(book);
//        }
    }
}


//Мы не может не добалять книги не удалять
