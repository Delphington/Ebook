package org.example.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestBookManager {

    //опасно хранить поля

    public RequestBookManager() {

    }


    public Optional<RequestBook> createRequestBook(Book book) {
        if (book.getAmount() > 0) {
            System.out.println("### Нельзя добавить запрос на книгу, их много");
            return Optional.empty();
        }
        RequestBook requestBook = new RequestBook(book);
        addRequestBook(requestBook);
        book.incrementReferences();
        System.out.println("### Запрос добавлен успешно");

        return Optional.of(requestBook);
    }


    public void addRequestBook(RequestBook requestBook) {
        RequestBook.requestBookList.add(requestBook);
    }


    public void closeRequest(Book book) {
        for (RequestBook requestBook : RequestBook.requestBookList) {
            if (requestBook.getBook().equalsBook(book)) {
                requestBook.setRequestBookStatus(RequestBookStatus.CLOSED);
            }
        }
    }


    public List<Book> getBookFromRequestBook() {
        List<Book> bookList = new ArrayList<>();
        for (RequestBook requestBook : RequestBook.requestBookList) {
            bookList.add(requestBook.getBook());
        }
        return bookList;
    }


    public void printRequestBook() {
        for (int i = 0; i < RequestBook.requestBookList.size(); i++) {
            if (RequestBook.requestBookList.get(i).getRequestBookStatus() == RequestBookStatus.OPEN) {
                System.out.println("{" + (i + 1) + "} " + RequestBook.requestBookList.get(i));
            }
        }
    }

}
