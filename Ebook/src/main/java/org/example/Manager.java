package org.example;

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


    public void changeAndAddBookStatus() {
        //Пользователь задает параметры книги
        Book tempbook = bookManager.createBook();
        log.info("Создана книга " + tempbook);

        //Книга есть в библиотеки
        Optional<Book> optional = bookManager.checkExistBook(tempbook);

        if (optional.isPresent()) {
            //Получили именно ту книгу, которая лежит в библиотеки
            Book book = optional.get();

            //Проверям книгу в Request
            if (!orderManager.updateRequestList(book)) {
                book.incrementAmount();
                orderManager.updateOrderList();
            }
        } else {
            System.out.println("### Ошибка. Такой книги нет!");
        }

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
