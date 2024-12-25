package org.example.model.book;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import org.example.model.SrvFileManager;
import org.example.model.exception.NoClearFileException;
import org.example.model.request.RequestBook;
import org.example.model.request.RequestBookStatus;


public class BookManager implements SrvFileManager {


    @Getter
    private Map<Long, Book> mapBooks = new TreeMap<>();
    @Getter // пока токо для контроллеров
    private List<Book> listBook;

    public BookManager(List<Book> listBook) {
        this.listBook = listBook;
        for (int i = 0; i < listBook.size(); i++) {
            mapBooks.put(listBook.get(i).getId(), listBook.get(i));
        }
    }

    public void deleteBook(Long id) {
        if (mapBooks.get(id).getAmount() == 0) {
            printStream.println("### Такая книга и так не доступна");
        } else {
            mapBooks.get(id).decrementAmount();
        }
    }


    // -------------------- Работа с файлами -------------------------------
    private Optional<Book> getBuildObject(String line, final Long id) {
        if (id != null && mapBooks.get(id) == null) {
            return Optional.empty();
        }

        Optional<String[]> optional = getParseLine(line);
        if (optional.isEmpty()) { // Плохо распарсилось
            return Optional.empty();
        }

        String[] arr = optional.get();

        //Сколько ожидаем уравнений
        if (arr.length < 10) {
            return Optional.empty();
        }

        Long bookId = null;
        String name;
        String author;
        LocalDate publishedDate;
        String description;
        Double price;
        Integer amount;
        StatusBookEnum statusBookEnum;
        Integer references;
        LocalDate lastDeliverDate;
        LocalDate lastSelleDate;
        try {
            bookId = Long.parseLong(arr[0]);
            name = arr[1];
            author = arr[2];
            publishedDate = LocalDate.parse(arr[3]);
            description = arr[4];
            price = Double.parseDouble(arr[5]);
            amount = Integer.parseInt(arr[6]);
            statusBookEnum = StatusBookEnum.valueOf(arr[7]);
            references = Integer.parseInt(arr[8]);
            lastDeliverDate = LocalDate.parse(arr[9]);
        } catch (RuntimeException e) {
            return Optional.empty();
        }

        if (id != null && !Objects.equals(id, bookId)) {
            return Optional.empty();
        }

        if (arr[10].equals("null")) {
            //todo: подумать
            lastSelleDate = LocalDate.now();
        } else {
            lastSelleDate = LocalDate.parse(arr[10]);
        }

        return Optional.of(new Book(bookId, name, author, publishedDate, description, price,
                amount, statusBookEnum, references, lastDeliverDate, lastSelleDate));

    }


    @Override
    public void importModel(Long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Book> optional = getBuildObject(line, id);
                if (optional.isPresent()) {
                    Book book = optional.get();
                    mapBooks.put(book.getId(), book);
                    printStream.println("### Книга импортирована");
                    return;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        printStream.println("### WARN: Импорта не было!");
    }


    @Override
    public void importAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Book> optional = getBuildObject(line, null);
                //Все отвественной за вывод на консоль происходит в builtBook
                if (optional.isPresent()) {
                    Book book = optional.get();
                    mapBooks.put(book.getId(), book);
                }
            }
            printStream.println("### Книги импортированны!");
            return;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        printStream.println("### WARN книги не импортировались");
    }


    //-------------------------------------------------------------

    public List<Book> updateListBook() {
        listBook = new ArrayList<>();
        for (Map.Entry<Long, Book> item : mapBooks.entrySet()) {
            listBook.add(item.getValue());
        }
        return listBook;
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
