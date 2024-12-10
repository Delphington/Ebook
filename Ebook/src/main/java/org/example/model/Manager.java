package org.example.model;

//todo обновляение статуса когда amount ?????

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@ToString
@Getter
@Setter
@Log4j2
public class Manager {
    private BookManager bookManager;
    private OrderManager orderManager;
    private RequestBookManager requestBookManager;

    public Manager(BookManager bookManager, OrderManager orderManager, RequestBookManager requestBookManager) {
        this.bookManager = bookManager;
        this.orderManager = orderManager;
        this.requestBookManager = requestBookManager;
    }


    public void changeAndAddBookStatus(Book book) {
        //Пользователь задает параметры книги

        //Проверям книгу в Request
        if (!orderManager.updateRequestList(book)) {
            book.incrementAmount();
            orderManager.updateOrderList();
        }

    }
}
