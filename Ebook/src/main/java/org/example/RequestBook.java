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


    private RequestBook(Book book) {
        this.book = book;
        this.requestBookStatus = RequestBookStatus.OPEN;
    }



    public static Optional<RequestBook> createRequestBook(Book book) {
        if(book.getAmount() >0 ){
            System.out.println("### Нельзя добавить запрос на книгу, их много");
            return Optional.empty();
        }
        RequestBook requestBook = new RequestBook(book);
        addRequestBook(requestBook);
        book.incrementReferences();
        return Optional.of(requestBook);
    }


    public static void addRequestBook(RequestBook requestBook) {
        requestBookList.add(requestBook);
    }



    @Override
    public String toString() {
        return "RequestBook{" +
                "book=" + book +
                ", requestBookStatus=" + requestBookStatus +
                '}';
    }

    public static void printRequestBook() {
        for (int i = 0; i < requestBookList.size(); i++) {
            if (requestBookList.get(i).requestBookStatus == RequestBookStatus.OPEN) {
                System.out.println("{" + (i + 1) + "} " + requestBookList.get(i));
            }
        }
    }

    //Добавление запроса, на книгу которой нету

    public static List<Book> getBookRequestSortedReference() {
        List<Book> books = new ArrayList<>();
        for (RequestBook requestBook : requestBookList) {
            books.add(requestBook.getBook());
        }
        return books.stream().sorted(Comparator.comparing(Book::getReferences).reversed()).collect(Collectors.toList());
    }
}
