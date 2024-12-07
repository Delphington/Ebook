package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@ToString
public class Order {

    private List<Book> bookListInOrder;
    private LocalDate finishDate;
    private Double amountSum = 0.0d;
    private Integer amountBook = 0;

    private StatusOrderEnum orderStatusEnum;


    public Order() {
        bookListInOrder = new ArrayList<>();
        finishDate =  LocalDate.now();
    }

    public void addBook(Book book) {
        bookListInOrder.add(book);
        amountSum += book.getPrice();
        amountBook++;
    }

    public void deleteBook(Book book) {
        bookListInOrder.remove(book);
        amountSum -= book.getPrice();
        amountBook--;
    }


}
