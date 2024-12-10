package org.example;


import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;


public class BookManager {
    @Getter
    private List<Book> listBook;
    private final PrintStream printStream = System.out;
    private final Scanner scanner = new Scanner(System.in);


    public BookManager(List<Book> listBook) {
        this.listBook = listBook;
    }

    public void deleteBook() {
        Book tempBook = createBook();
        Optional<Book> optional = checkExistBook(tempBook);

        if (optional.isPresent()) {
            Book book = optional.get();
            if (book.getAmount() == 0) {
                printStream.println("### Такая книга и так не доступна");
            } else {
                book.decrementAmount();
            }
        } else {
            printStream.println("### Такой книги нет в библиотеки");
        }

    }


    public Optional<Book> checkExistBook(Book book) {
        for (Book item : listBook) {
            if (item.equals(book)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }


    public void addBook(Book book) {
        listBook.add(book);
    }


    public Book createBook() {
        String nameBook;
        String nameAuthor;
        LocalDate publishedData;
        String description;
        Double price;

        printStream.print("Введите название книги: ");
        nameBook = scanner.nextLine().trim();

        printStream.print("Введите автора книги: ");
        nameAuthor = scanner.nextLine().trim();

        while (true) {
            try {
                printStream.print("Введите дату издания: ");
                publishedData = LocalDate.parse(scanner.nextLine().trim());
                break;
            } catch (RuntimeException e) {
                printStream.println("Дата введена неверно попробуйте еще раз!");
            }
        }
        printStream.print("Вывведите описание книги: ");
        description = scanner.nextLine().trim();

        while (true) {
            try {
                printStream.print("Введите стоимость книги: ");
                price = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (IllegalArgumentException e) {
                printStream.println("Цена введена неверно попробуйте еще раз!");
            }
        }
        return new Book(nameBook, nameAuthor, publishedData,
                description, price, 1);
    }


    //Список залежавшихся книг не проданные больше 6 месяцев
    public List<Book> getStaleBook(List<Book> books) {
        return books.stream()
                .filter(i -> ChronoUnit.MONTHS.between(i.getLastDeliverDate(), i.getLastSelleDate()) > 6)
                .collect(Collectors.toList());
    }


    public List<Book> sortByName(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getName).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> sorByPrice(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> sortByDate(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getPublishedData).reversed())
                .collect(Collectors.toList());
    }

    public List<Book> sortByStatus(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getStatusBookEnum).reversed())
                .collect(Collectors.toList());
    }

}
