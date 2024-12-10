package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@ToString


//ID детали заказа
public class Order {
    private LocalDate finishDate;
    private LocalDate createDate;
    private LocalDate completedDate;
    private Double amountSum = 0.0d;
    private Integer amountBook = 0;
    private List<Book> bookListInOrder;
    private StatusOrderEnum orderStatusEnum;
    //Todo: плохая зависимость
    private static RequestBookManager requestBookManager = new RequestBookManager();

    public Order() {
        bookListInOrder = new ArrayList<>();
        createDate = LocalDate.now();
        orderStatusEnum = StatusOrderEnum.DONE;
    }

    //Если ли бук в листе??
    public void addBook(Book book) {
        if(orderStatusEnum == StatusOrderEnum.CANCEL){
            System.out.println("В отмененный заказ нельзя добавить книги!");
            return;
        }

        bookListInOrder.add(book);
        amountSum += book.getPrice();
        if (book.getAmount() > 0) {
            System.out.println("### Книги есть! Книга добалвенна");
        } else {
            orderStatusEnum = StatusOrderEnum.NEW;
            requestBookManager.createRequestBook(book);
            book.incrementReferences();
            System.out.println("### Книги нет! Книга добавленна в запрос");
        }
    }








    public boolean completedOrder() {
        for (Book item : bookListInOrder) {
            if (item.getAmount() == 0) {
                System.out.println("Не все книги доступны");
                return false;
            }
        }

        for (Book item : bookListInOrder) {
            item.incrementAmount();
            item.setLastSelleDate(LocalDate.now());
        }
        orderStatusEnum = StatusOrderEnum.DONE;
        return true;
    }

    public void cancelOrder() {
        finishDate = LocalDate.now();
        orderStatusEnum = StatusOrderEnum.CANCEL;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Order order = (Order) object;
        return Objects.equals(finishDate, order.finishDate) && Objects.equals(createDate, order.createDate) && Objects.equals(completedDate, order.completedDate) && Objects.equals(amountSum, order.amountSum) && Objects.equals(amountBook, order.amountBook) && Objects.equals(bookListInOrder, order.bookListInOrder) && orderStatusEnum == order.orderStatusEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(finishDate, createDate, completedDate, amountSum, amountBook, bookListInOrder, orderStatusEnum);
    }


}
