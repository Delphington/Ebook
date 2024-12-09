package org.example;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@Setter
public class Manager {
    private BookManager bookManager;
    private OrderManager orderManager;

    public Manager(BookManager bookManager, OrderManager orderManager) {
        this.bookManager = bookManager;
        this.orderManager = orderManager;
    }


    public void changeAndAddBookStatus() {
        //Пользователь задает параметры книги
        Book book =  bookManager.createBook();
        if(bookManager.checkExistBook(book)){
            book.incrementAmount();
            orderManager.updateOrderList(book);
            System.out.println("### Такая книга уже существует, поэтому мы увеличим счетчик!");
        }else{
            book.setStatusBookEnum(StatusBookEnum.AVAILABLE);
            book.setLastDeliverDate(LocalDate.now());
            bookManager.addBook(book);
            System.out.println("### Книга добавленна!");
        }
    }
}
