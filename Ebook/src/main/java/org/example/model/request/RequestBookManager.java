package org.example.model.request;

import lombok.Setter;
import org.example.model.SrvFileManager;
import org.example.model.book.Book;
import org.example.model.book.BookManager;
import org.example.model.book.StatusBookEnum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public Optional<RequestBook> findById(Long id) {
        for (RequestBook requestBook : RequestBook.requestBookList) {
            if (requestBook.getId().equals(id)) {
                return Optional.of(requestBook);
            }
        }
        return Optional.empty();
    }


    @Override
    public void exportAll() {
        if (!clearFile(EXPORT_FILE_REQUEST_BOOK)) {
            printStream.println("### Ошибка очистки файла файлами");
            return;
        }

        boolean flag = true;

        for (int i = 0; i < RequestBook.requestBookList.size(); i++) {
            try {
                if (flag) {
                    String title = RequestBook.requestBookList.get(i).generateTitle();
                    RequestBook.requestBookList.get(i).writeTitle(EXPORT_FILE_REQUEST_BOOK, title);
                    flag = false;
                }
                RequestBook.requestBookList.get(i).writeDate(EXPORT_FILE_REQUEST_BOOK);
            } catch (RuntimeException | IOException e) {
                printStream.println("### Запись не произошла! ");
                return;
            }
        }
        printStream.println("### Успешно все записалось в файл!");
    }

    @Override
    public void importAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_REQUEST_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<RequestBook> optionalRequestBookFromFile = getParseBook(line);
                if (optionalRequestBookFromFile.isPresent()) {
                    RequestBook requestBookFromFile = optionalRequestBookFromFile.get();
                    Optional<RequestBook> requestBookOptional = findById(requestBookFromFile.getId());
                    if (requestBookOptional.isPresent()) {
                        requestBookOptional.get().of(requestBookFromFile);
                    } else {
                        //добавляем
                        addRequestBook(requestBookFromFile);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    private Optional<RequestBook> getParseBook(String input) {
        Optional<String[]> optional = getParseLine(input);

        if (optional.isPresent()) {
            String[] arr = optional.get();
            if (arr.length != 3) {
                return Optional.empty();
            }
            Long requestBookId;
            Long bookId;
            RequestBookStatus requestBookStatus;
            try {
                requestBookId = Long.parseLong(arr[0]);
                bookId = Long.parseLong(arr[1]);
                requestBookStatus = RequestBookStatus.valueOf(arr[2]);
            } catch (RuntimeException e) {
                System.err.println("Ошибка: преобразование объекта");
                return Optional.empty();
            }

            Optional<Book> optionalBook = bookManager.findById(bookId);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                //todo: про количество книг
                return Optional.of(new RequestBook(requestBookId, book, requestBookStatus));

            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
