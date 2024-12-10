package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class RequestBook {
    private Book book;
    private RequestBookStatus requestBookStatus;

    @Getter
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
