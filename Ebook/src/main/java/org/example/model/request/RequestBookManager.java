package org.example.model.request;

import lombok.Setter;
import org.example.model.SrvFileManager;
import org.example.model.book.Book;
import org.example.model.book.BookManager;
import org.example.model.book.StatusBookEnum;
import org.example.model.exception.NoClearFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDate;
import java.util.*;

public class RequestBookManager implements SrvFileManager {

    //опасно хранить поля
    @Setter
    private BookManager bookManager;


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
            if (requestBook.getBook().equals(book)) {
                book.decrementReferences();
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


    public void printRequestOpenBook() {
        for (int i = 0; i < RequestBook.requestBookList.size(); i++) {
            if (RequestBook.requestBookList.get(i).getRequestBookStatus() == RequestBookStatus.OPEN) {
                System.out.println("{" + (i + 1) + "} " + RequestBook.requestBookList.get(i));
            }
        }
    }

    // ---------------------- Для работы с файлами -----------------------------------------
//--------
    private Optional<RequestBook> getBuildObject(String line, final Long id) {
        Optional<String[]> optional = getParseLine(line);
        if (optional.isEmpty()) { // Плохо распарсилось
            return Optional.empty();
        }
        String[] arr = optional.get();

        //Сколько ожидаем уравнений
        if (arr.length != 3) {
            return Optional.empty();
        }

        Long requestId = null;
        Long bookId = null;
        RequestBookStatus requestBookStatus = null;
        try {
            requestId = Long.parseLong(arr[0]);
            bookId = Long.parseLong(arr[1]);
            requestBookStatus = RequestBookStatus.valueOf(arr[2]);
        } catch (RuntimeException e) {
            return Optional.empty(); //Не верное преобразование
        }

        //todo: про количество книг

        if (bookManager.getMapBooks().get(bookId) == null) {
            System.out.println("### Ошибка: Запрос на книгу, которой нет в библио");
            return Optional.empty();
        }


        //Проверка друг id
        if (id != null && !Objects.equals(requestId, id)) {
            return Optional.empty();
        }

        return Optional.of(new RequestBook(requestId, bookManager.getMapBooks().get(bookId), requestBookStatus));
    }


    @Override
    public void importModel(Long id) {
        //Проверка что такая книга есть
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_REQUEST_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<RequestBook> optional = getBuildObject(line, id);
                if (optional.isPresent()) {
                    RequestBook requestBook = optional.get();
                    //Обновление есть
                    Optional<RequestBook> requestBookOptional = findById(requestBook.getId(), RequestBook.requestBookList);
                    if (requestBookOptional.isPresent()) {
                        requestBookOptional.get().copyOf(requestBook);
                        printStream.println("### Книга импортирована и обновленна");
                        return;

                    } else if (requestBook.getBook().getAmount() <= 0) {
                        RequestBook.requestBookList.add(requestBook);
                        printStream.println("### Книга импортирована и добаленна");
                        return;

                    }
                    printStream.println("### Книг много, на них нельзя сделать запрос!");
                    return;

                }
            }
            printStream.println("### Не получилось импортировать");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public void importAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_REQUEST_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<RequestBook> optionalRequestBookFromFile = getBuildObject(line, null);
                if (optionalRequestBookFromFile.isPresent()) {
                    RequestBook requestBookFromFile = optionalRequestBookFromFile.get();
                    Optional<RequestBook> requestBookOptional = findById(requestBookFromFile.getId(), RequestBook.requestBookList);
                    if (requestBookOptional.isPresent()) {
                        requestBookOptional.get().copyOf(requestBookFromFile);
                    } else {
                        addRequestBook(requestBookFromFile);
                    }
                }
            }
            System.out.println("### Файла импортированы! ");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
