package org.example.model;


import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;


public class BookManager implements SrvFileManager {
    @Getter
    private List<Book> listBook;
    private final PrintStream printStream = System.out;

    public BookManager(List<Book> listBook) {
        this.listBook = listBook;
    }

    public void deleteBook(Book book) {
        if (book.getAmount() == 0) {
            printStream.println("### Такая книга и так не доступна");
        } else {
            book.decrementAmount();
        }
    }

    @Override
    public void writeToFile() {
        if (!clearFile(FILE_TO_WRITE)) {
            printStream.println("### Ошибка очистки файла файлами");
            return;
        }

        for (int i = 0; i < listBook.size(); i++) {
            try {
                listBook.get(i).writeDate(FILE_TO_WRITE);
            } catch (RuntimeException | IOException e) {
                printStream.println("### Запись не произошла! ");
                return;
            }
        }
        printStream.println("### Успешно все записалось в файл!");
    }


    //Список залежавшихся книг не проданные больше 6 месяцев
    public List<Book> getStaleBook(List<Book> books) {
        return books.stream().filter(book -> (book.getLastDeliverDate() != null && book.getLastSelleDate() != null))
                .filter(i -> ChronoUnit.MONTHS.between(i.getLastDeliverDate(), i.getLastSelleDate()) > 6)
                .collect(Collectors.toList());
    }


    public List<Book> sortByNameBook(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getName).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> sorByPrice(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> sorByReference(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getReferences).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> sortByDatePublished(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getPublishedData).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> sortByDeliverDate(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getLastDeliverDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> sortByAmount(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getAmount).reversed())
                .collect(Collectors.toList());
    }

}
