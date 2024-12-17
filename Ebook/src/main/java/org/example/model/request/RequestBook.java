package org.example.model.request;

import lombok.Getter;
import lombok.Setter;
import org.example.model.book.Book;

import java.util.*;

@Getter
@Setter
public class RequestBook {
    private int ID = 0;
    private static int counterID = 1;
    private Book book;
    private RequestBookStatus requestBookStatus;
    public static List<RequestBook> requestBookList = new ArrayList<>();

    public RequestBook(Book book) {
        this.book = book;
        this.requestBookStatus = RequestBookStatus.OPEN;
        ID = counterID;
        counterID++;
    }

    @Override
    public String toString() {
        return "RequestBook{" +
               "ID=" +ID +
               ", book=" + book +
               ", requestBookStatus=" + requestBookStatus +
               '}';
    }
}
