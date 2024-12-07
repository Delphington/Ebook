package org.example;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class BookManager {
    @Getter
    private List<Book> listBook;

    public BookManager() {
        listBook = new ArrayList<>();
        listBook.add(new Book("book1", StatusBookEnum.AVAILABLE));
        listBook.add(new Book("book2", StatusBookEnum.AVAILABLE));
        listBook.add(new Book("book3", StatusBookEnum.ABSENT));
    }

    public void removeBook(Book book){
        book.setStatusBookEnum(StatusBookEnum.ABSENT);
    }

    public void addBook(Book book){
        listBook.add(book);
    }

}
