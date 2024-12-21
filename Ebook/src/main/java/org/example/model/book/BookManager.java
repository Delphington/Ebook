package org.example.model.book;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import org.example.model.SrvFileManager;


public class BookManager implements SrvFileManager {

    private final PrintStream printStream = System.out;
    @Getter
    private Map<Long, Book> mapBooks = new TreeMap<>();
    @Getter
    private List<Book> listBook;

    public BookManager(List<Book> listBook) {
        this.listBook = listBook;
        for (int i = 0; i < listBook.size(); i++) {
            mapBooks.put(listBook.get(i).getId(), listBook.get(i));
        }
    }

    public void deleteBook(Book book) {
        if (book.getAmount() == 0) {
            printStream.println("### Такая книга и так не доступна");
        } else {
            book.decrementAmount();
        }
    }



    // -------------------- Работа с файлами -------------------------------
    public Optional<Book> findById(Long id) {
        for (Book book : listBook) {
            if (book.getId().equals(id)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }



    @Override
    public void exportAll() {
        if (!clearFile(EXPORT_FILE_BOOK)) {
            printStream.println("### Ошибка очистки файла файлами");
            return;
        }

        boolean flag = true;

        for (Map.Entry<Long, Book> map : mapBooks.entrySet()) {
            try {
                //Логика записывания заголовка
                if (flag) {
                    String title = map.getValue().generateTitle();
                    map.getValue().writeTitle(EXPORT_FILE_BOOK, title);
                    flag = false;
                }
                map.getValue().writeDate(EXPORT_FILE_BOOK);
            } catch (RuntimeException | IOException e) {
                printStream.println("### Запись не произошла! ");
                return;
            }
        }
        printStream.println("### Успешно все записалось в файл!");
    }


    private Optional<Book> getParseBook(String input) {
        Optional<String[]> optional = getParseLine(input);
        if (optional.isPresent()) {
            String[] arr = optional.get();
            //MAGIC NUMBER -  количество полей
            if (arr.length != 11) {
                return Optional.empty();
            }

            try {
                Long ID = Long.parseLong(arr[0]);

                if (mapBooks.get(ID) == null) {
                    //Нет книги такой
                    return Optional.empty();
                }
                String name = arr[1];
                String author = arr[2];
                LocalDate publishedDate = LocalDate.parse(arr[3]);
                String description = arr[4];
                Double price = Double.parseDouble(arr[5]);
                Integer amount = Integer.parseInt(arr[6]);
                StatusBookEnum statusBookEnum = StatusBookEnum.valueOf(arr[7]);
                Integer references = Integer.parseInt(arr[8]);
                LocalDate lastDeliverDate = LocalDate.parse(arr[9]);
                LocalDate lastSelleDate;

                if (arr[10].equals("null")) {
                    //todo: подумать
                    lastSelleDate = LocalDate.now();
                } else {
                    lastSelleDate = LocalDate.parse(arr[10]);
                }

                Book book = new Book(ID, name, author, publishedDate, description, price,
                        amount, statusBookEnum, references, lastDeliverDate, lastSelleDate);
                return Optional.of(book);

            } catch (RuntimeException e) {
                System.out.println("Ошибка: преобразование объекта");
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public void importAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Book> optional = getParseBook(line);

                if (optional.isPresent()) {
                    Book book = optional.get();
                    mapBooks.put(book.getId(), book);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    //-------------------------------------------------------------

    public List<Book> generateList() {
        listBook.clear();
        for (Map.Entry<Long, Book> map : mapBooks.entrySet()) {
            listBook.add(map.getValue());
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
