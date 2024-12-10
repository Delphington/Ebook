package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RequestBook {
    private Book book;
    private RequestBookStatus requestBookStatus;

    @Getter
    public static List<RequestBook> requestBookList = new ArrayList<>();


    private RequestBook(Book book) {
        this.book = book;
        this.requestBookStatus = RequestBookStatus.OPEN;
    }


    public static RequestBook createRequestBook(Book book) {
        RequestBook requestBook = new RequestBook(book);
        addRequestBook(requestBook);
        return requestBook;
    }


    public static void addRequestBook(RequestBook requestBook) {
        requestBookList.add(requestBook);
    }

}
