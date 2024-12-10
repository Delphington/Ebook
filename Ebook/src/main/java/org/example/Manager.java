package org.example;

//todo обновляение статуса когда amount ?????

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.Optional;

@ToString
@Getter
@Setter
@Log4j2
public class Manager {
    private BookManager bookManager;
    private OrderManager orderManager;

    public Manager(BookManager bookManager, OrderManager orderManager) {
        this.bookManager = bookManager;
        this.orderManager = orderManager;
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
            if(!orderManager.updateRequestList(book)){
                book.incrementAmount();
                orderManager.updateOrderList();
            }
        } else {
            System.out.println("### Ошибка. Такой книги нет!");
        }

    }
}
