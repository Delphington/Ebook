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



    //todo: рефакторинг лишних выводов, И титульной строки

    @Override
    public void exportAll() {
        if (!clearFile(EXPORT_FILE_BOOK)) {
            throw new NoClearFileException("### Ошибка очистки файла файла!");
        }

        boolean flag = true;

        for (Map.Entry<Long, Book> map : mapBooks.entrySet()) {
            try {
                //Логика записывания заголовка
                if (flag) {
                    map.getValue().writeTitle(EXPORT_FILE_BOOK, map.getValue().generateTitle());
                    flag = false;
                }
                map.getValue().writeDate(EXPORT_FILE_BOOK);
            } catch (RuntimeException e) {
                printStream.println("### Ошибка Запись не произошла! ");
                return;
            }
        }
        printStream.println("### Успешно все записалось в файл!");
    }

    @Override
    public void importModel(Long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Book> optional = builtBook(line, id);
                if (optional.isPresent()) {
                    Book book = optional.get();
                    mapBooks.put(book.getId(), book);
                    printStream.println("### Книга импортирована");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    @Override
    public void importAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_BOOK))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Book> optional = createBookFromFile(getParseLine(line).get());
                //Все отвественной за вывод на консоль происходит в builtBook
                if (optional.isPresent()) {
                    Book book = optional.get();
                    mapBooks.put(book.getId(), book);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private Optional<Book> builtBook(String input, Long id) {
        if (mapBooks.get(id) == null) {
            System.out.println("### Такой книги нет В библиотеке");
            return Optional.empty();
        }

        Optional<String[]> optional = getParseLine(input);
        if (optional.isPresent() && optional.get().length > 1) {
            String[] arr = optional.get();
            try {
                Long idTemp = Long.parseLong(arr[0]);

                if (Objects.equals(idTemp, id)) {
                    return createBookFromFile(arr);
                }

            } catch (RuntimeException e) {
                System.out.println("### Ошибка: преобразование объекта");
                return Optional.empty();
            }
        }
        return Optional.empty();
    }


    private Optional<Book> createBookFromFile(String[] arr) {
        try {
            Long id = Long.parseLong(arr[0]);

            if (mapBooks.get(id) == null) {
                printStream.println("### Ошибка. Книги нет в библиотеке, нельзя импортировать");
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

            return Optional.of(new Book(id, name, author, publishedDate, description, price,
                    amount, statusBookEnum, references, lastDeliverDate, lastSelleDate));
        } catch (RuntimeException e) {
            System.out.println("### Не корректное преобразование строк");
            return Optional.empty();
        }
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
