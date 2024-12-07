package org.example;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ToString
public class BookManager {
    @Getter
    private List<Book> listBook;

    public BookManager(List<Book> listBook) {
        this.listBook = listBook;
    }


    public void removeBook(Book book) {
        book.setStatusBookEnum(StatusBookEnum.NOT_AVAILABLE);
    }

    public void addBook(Book book) {
        listBook.add(book);
    }


    public List<Book> sortByName(){
        return listBook.stream()
                .sorted(Comparator.comparing(Book::getName).reversed())
                .collect(Collectors.toList());
    }
    public List<Book>  sorByPrice(){
        return listBook.stream()
                .sorted(Comparator.comparing(Book::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<Book>  sortByDate(){
        return listBook.stream()
                .sorted(Comparator.comparing(Book::getPublishedData).reversed())
                .collect(Collectors.toList());
    }

    public List<Book>  sortByStatus(){
        return listBook.stream()
                .sorted(Comparator.comparing(Book::getStatusBookEnum).reversed())
                .collect(Collectors.toList());
    }

}
