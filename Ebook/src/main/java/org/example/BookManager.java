package org.example;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@ToString
public class BookManager {
    @Getter
    private List<Book> listBook;

    public BookManager()   {
        listBook = new ArrayList<>();
        listBook.add(new Book("The Lord of the Rings","Mickail", LocalDate.of(2024, 2, 23), 25.99, StatusBookEnum.AVAILABLE));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        listBook.add(new Book("harry","Mickail2", LocalDate.of(2022, 2, 23), 100.99, StatusBookEnum.AVAILABLE));
        listBook.add(new Book("Potter","Mickail3", LocalDate.of(2023, 2, 23), 10.99, StatusBookEnum.ABSENT));

    }

    public void removeBook(Book book) {
        book.setStatusBookEnum(StatusBookEnum.ABSENT);
    }

    public void addBook(Book book) {
        listBook.add(book);
    }


    public void sortByName(){
        Collections.sort(listBook, Comparator.comparing(Book::getPublishedData));
    }
    public void sorByPrice(){
        Collections.sort(listBook, Comparator.comparing(Book::getPrice));
    }

    public void sortByDate(){
        Collections.sort(listBook, Comparator.comparing(Book::getPublishedData));
    }

    public void sortByStatus(){
        Collections.sort(listBook, Comparator.comparing(Book::getStatusBookEnum));
    }

    public static void main(String[] args) {
        BookManager bookManager = new BookManager();
        System.out.println(bookManager);

        bookManager.sortByStatus();
        System.out.println(bookManager);

    }


}
