package org.example;


import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Hello world!
 */
public class Main {
    private static final PrintStream printStream =  System.out;

    public static void main(String[] args) {

        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", LocalDate.of(1954, 7, 29), 25.99, StatusBookEnum.AVAILABLE);
        Book book2 = new Book("Pride and Prejudice", "Jane Austen", LocalDate.of(1813, 1, 28), 12.99, StatusBookEnum.NOT_AVAILABLE);
        Book book3 = new Book("To Kill a Mockingbird", "Harper Lee", LocalDate.of(1960, 7, 11), 15.99, StatusBookEnum.AVAILABLE);
        Book book4 = new Book("1984", "George Orwell", LocalDate.of(1949, 6, 8), 10.99, StatusBookEnum.NOT_AVAILABLE);
        Book book5 = new Book("The Great Gatsby", "F. Scott Fitzgerald", LocalDate.of(1925, 4, 10), 18.99, StatusBookEnum.AVAILABLE);

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);

        BookManager bookManager = new BookManager(bookList);
        Order order = new Order();
        order.addBook(book1);
        order.addBook(book2);

        printStream.println("Заказ");
        printStream.println(order);




        System.out.println();

    }
}
