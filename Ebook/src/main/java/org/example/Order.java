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
    private LocalDate finishDate;
    private LocalDate createDate;
    private LocalDate completedDate;
    private Double amountSum = 0.0d;
    private Integer amountBook = 0;

    private List<CellBook> bookListInOrder;
    private List<RequestBook> requestBookList;
    private StatusOrderEnum orderStatusEnum;


    public Order() {
        bookListInOrder = new ArrayList<>();
        requestBookList = new ArrayList<>();
        createDate = LocalDate.now();
        orderStatusEnum = StatusOrderEnum.NEW;
    }


    public void completedOrder() {
        finishDate = LocalDate.now();
        orderStatusEnum = StatusOrderEnum.DONE;
    }
    public void cancelOrder(){
        //todo:
        finishDate = LocalDate.now();
        orderStatusEnum = StatusOrderEnum.CANCEL;
    }





    public void addBook(CellBook book) {
        if(book.getStatusBookEnum() == StatusBookEnum.AVAILABLE){
            bookListInOrder.add(book); //todo: с деньгами
        }
        //Когда нет книги
        else{
            requestBookList.add(new RequestBook(book, RequestBookStatus.OPEN));
            System.out.println("Книги нет! Книга добалвенна в запрос");
        }

    }

    public void deleteBook(CellBook book) {
        bookListInOrder.remove(book);
        amountSum -= book.getPrice();
        amountBook--;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Order order = (Order) object;
        return Objects.equals(bookListInOrder, order.bookListInOrder) && Objects.equals(createDate, order.createDate) && Objects.equals(completedDate, order.completedDate) && Objects.equals(amountSum, order.amountSum) && Objects.equals(amountBook, order.amountBook) && Objects.equals(requestBookList, order.requestBookList) && orderStatusEnum == order.orderStatusEnum;
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(bookListInOrder, finishDate, createDate, completedDate, amountSum, amountBook, requestBookList, orderStatusEnum);
//    }
}
