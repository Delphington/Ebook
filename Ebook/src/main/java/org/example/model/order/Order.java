package org.example.model.order;

import lombok.Getter;
import lombok.Setter;
import org.example.model.SrvExporter;
import org.example.model.request.RequestBookManager;
import org.example.model.book.Book;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
//ID детали заказа
public class Order implements SrvExporter {
    private Long id;
    private LocalDate createDate;
    private LocalDate completedDate;
    private Double amountSum = 0.0d;
    private List<Book> bookListInOrder;
    private StatusOrderEnum orderStatusEnum;

    private static long counterID = 1;
    private static RequestBookManager requestBookManager = new RequestBookManager();


    public Order() {
        bookListInOrder = new ArrayList<>();
        createDate = LocalDate.now();
        orderStatusEnum = StatusOrderEnum.DONE;
        id = counterID;
        counterID++;
    }

    public Order(Long id, LocalDate createDate, LocalDate completedDate, Double amountSum, StatusOrderEnum statusOrderEnum) {
        bookListInOrder = new ArrayList<>();
        this.id = id;
        this.createDate = createDate;
        this.completedDate = completedDate;
        this.amountSum = amountSum;
        this.orderStatusEnum = orderStatusEnum;
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

    public void coyOf(Order order) {
        id = order.getId();
        createDate = order.getCreateDate();
        completedDate = order.getCompletedDate();
        amountSum = order.getAmountSum();
        bookListInOrder = order.getBookListInOrder();
        orderStatusEnum = order.getOrderStatusEnum();
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        return id == ((Order) object).getId();
    }

    @Override
    public String toString() {
        return "Order{" +
               "ID=" + id +
               ", createDate=" + createDate +
               ", completedDate=" + completedDate +
               ", amountSum=" + amountSum +
               ", amountBook=" + bookListInOrder.size() +
               ", bookListInOrder=" + bookListInOrder +
               ", orderStatusEnum=" + orderStatusEnum +
               '}';
    }

    //------------- Для работы с файлами -------------------------------
    @Override
    public String generateInfoObject() {
        StringBuilder temp = new StringBuilder();
        temp.append(id).append(DEFAULT_DELIMITER);
        temp.append(createDate).append(DEFAULT_DELIMITER);
        temp.append(completedDate).append(DEFAULT_DELIMITER);
        temp.append(amountSum).append(DEFAULT_DELIMITER);
        temp.append(orderStatusEnum);

        if (bookListInOrder.size() == 0) {
            temp.append("\n");
        } else {
            temp.append(DEFAULT_DELIMITER);
        }
        for (int i = 0; i < bookListInOrder.size(); i++) {
            temp.append(bookListInOrder.get(i).getId());
            if (i != bookListInOrder.size() - 1) {
                temp.append(DEFAULT_DELIMITER);
            } else {
                temp.append("\n");
            }

        }
        return temp.toString();
    }

    @Override
    public String generateTitle() {
        return "ID:createDate:completedDate:amountSum:orderStatusEnum:bookId_N\n";
    }
}
