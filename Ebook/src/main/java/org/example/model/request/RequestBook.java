package org.example.model.request;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Item;
import org.example.model.book.Book;

import java.util.*;

@Getter
@Setter
public class RequestBook implements Item {
    private Long id;
    private static long counterID = 1;
    private Book book;
    private RequestBookStatus requestBookStatus;
    public static List<RequestBook> requestBookList = new ArrayList<>();

    public RequestBook(Book book) {
        this.book = book;
        this.requestBookStatus = RequestBookStatus.OPEN;
        id = counterID;
        counterID++;
    }

    //Для файлов
    public RequestBook(Long id, Book book, RequestBookStatus requestBookStatus) {
        this.id = id;
        this.book = book;
        this.requestBookStatus = requestBookStatus;
    }


    @Override
    public String toString() {
        return "RequestBook{" +
               "ID=" + id +
               ", book=" + book +
               ", requestBookStatus=" + requestBookStatus +
               '}';
    }

    //------------- Для работы с файлами -------------------------------

    public void copyOf(RequestBook requestBook) {
        this.book = requestBook.book;
        this.requestBookStatus = requestBook.requestBookStatus;
    }


    @Override
    public String generateInfoObject() {
        StringBuilder temp = new StringBuilder();
        temp.append(id).append(DEFAULT_DELIMITER);
        temp.append(book.getId()).append(DEFAULT_DELIMITER);
        temp.append(requestBookStatus).append("\n");
        return temp.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        RequestBook requestBookTemp = (RequestBook) object;
        return Objects.equals(id, requestBookTemp.getId());
    }
}