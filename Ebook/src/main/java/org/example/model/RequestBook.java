package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RequestBook {
    private Book book;
    private RequestBookStatus requestBookStatus;
    public static List<RequestBook> requestBookList = new ArrayList<>();

    public RequestBook(Book book) {
        this.book = book;
        this.requestBookStatus = RequestBookStatus.OPEN;
    }

    @Override
    public String toString() {
        return "RequestBook{" +
                "book=" + book +
                ", requestBookStatus=" + requestBookStatus +
                '}';
    }
}
