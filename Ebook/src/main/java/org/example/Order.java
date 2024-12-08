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

    private List<CellBook> bookListInOrder;
    private LocalDate finishDate;
    private Double amountSum = 0.0d;
    private Integer amountBook = 0;
    private List<RequestBook> requestBookList;

    private StatusOrderEnum orderStatusEnum;


    public Order() {
        bookListInOrder = new ArrayList<>();
        requestBookList = new ArrayList<>();
        finishDate = LocalDate.now();
    }

    public void addBook(CellBook book) {
        if (book.getStatusBookEnum() == StatusBookEnum.NOT_AVAILABLE) {
            requestBookList.add(new RequestBook(book, RequestBookStatus.OPEN));
            System.out.println("Пока что такой книги нет в наличии");
        }

        bookListInOrder.add(book);
        amountSum += book.getPrice();
        amountBook++;
    }

    public void deleteBook(CellBook book) {
        bookListInOrder.remove(book);
        amountSum -= book.getPrice();
        amountBook--;
    }
}
