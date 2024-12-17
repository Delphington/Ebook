package org.example.model.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.model.DataObjExporter;
import org.example.model.request.RequestBookManager;
import org.example.model.book.Book;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@ToString


//ID детали заказа
public class Order implements DataObjExporter {
    private int ID;
    private LocalDate createDate;
    private LocalDate completedDate;
    private Double amountSum = 0.0d;
    private Integer amountBook = 0;
    private List<Book> bookListInOrder;
    private StatusOrderEnum orderStatusEnum;

    private static int counterID = 1;
    private static RequestBookManager requestBookManager = new RequestBookManager();

    @Override
    public String toString() {
        return "Order{" +
               "ID=" + ID +
               ", createDate=" + createDate +
               ", completedDate=" + completedDate +
               ", amountSum=" + amountSum +
               ", amountBook=" + amountBook +
               ", bookListInOrder=" + bookListInOrder +
               ", orderStatusEnum=" + orderStatusEnum +
               '}';
    }

    public Order() {
        bookListInOrder = new ArrayList<>();
        createDate = LocalDate.now();
        orderStatusEnum = StatusOrderEnum.DONE;
        ID = counterID;
        counterID++;
    }

    //Если ли бук в листе??
    public void addBook(Book book) {
        if (orderStatusEnum == StatusOrderEnum.CANCEL) {
            System.out.println("### В отмененный заказ нельзя добавить книги!");
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
        completedDate = LocalDate.now();
        orderStatusEnum = StatusOrderEnum.CANCEL;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        return ID == ((Order) object).getID();
    }


    @Override
    public String generateString() {
        StringBuilder temp = new StringBuilder();
        temp.append(ID).append(DEFAULT_DELIMITER);
        temp.append(createDate).append(DEFAULT_DELIMITER);
        temp.append(completedDate).append(DEFAULT_DELIMITER);
        temp.append(amountSum).append(DEFAULT_DELIMITER);
        temp.append(amountBook).append(DEFAULT_DELIMITER);
        temp.append(bookListInOrder).append(DEFAULT_DELIMITER);
        temp.append(orderStatusEnum).append('\n');
        return temp.toString();

        //ID:createDate:completedDate:amountSum:amountBook: bookListInOrder:orderStatusEnum;
    }
}
