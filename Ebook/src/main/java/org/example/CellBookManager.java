package org.example;


import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;

public class CellBookManager {
    @Getter
    private List<CellBook> listBook;
    private final PrintStream printStream = System.out;
    private final Scanner scanner = new Scanner(System.in);


    public CellBookManager(List<CellBook> listBook) {
        this.listBook = listBook;
    }


    //todo: вынести основные данные запроса книги в интерфейс
    public void deleteBook() {
        String nameBook;
        String nameAuthor;
        LocalDate publishedData;
        printStream.print("Введите название книги: ");
        nameBook = scanner.nextLine().trim();

        printStream.print("Введите автора книги: ");
        nameAuthor = scanner.nextLine().trim();

        while (true) {
            try {
                printStream.print("Вывведите дату издания: ");
                publishedData = LocalDate.parse(scanner.nextLine().trim());
                break;
            } catch (IllegalArgumentException e) {
                printStream.println("Дата введена неверно попробуйте еще раз!");
            }
        }


        for (int i = 0; i < listBook.size(); i++) {
            if (Objects.equals(listBook.get(i).getName(), nameBook)
                    && Objects.equals(listBook.get(i).getAuthor(), nameAuthor)
                    && Objects.equals(listBook.get(i).getPublishedData(), publishedData)) {
                deleteBook(listBook.get(i));
                printStream.println("## Книга удаленна успешно!");
                return;
            }
        }
        printStream.println("## Книга не найдена!");

    }


    public boolean checkExistBook(CellBook cellBook) {
        for (CellBook book : listBook) {
            if (cellBook.equals(book)) {
                return true;
            }
        }
        return false;
    }


    public void deleteBook(CellBook book) {
        listBook.remove(book);
    }


    public void addBook(CellBook book) {
        listBook.add(book);
    }


    public void addBook() {
        String nameBook;
        String nameAuthor;
        LocalDate publishedData;
        String description;
        Double price;
        printStream.print("Вывведите название книги: ");
        nameBook = scanner.nextLine().trim();

        printStream.print("Вывведите автора книги: ");
        nameAuthor = scanner.nextLine().trim();

        while (true) {
            try {
                printStream.print("Вывведите дату издания: ");
                publishedData = LocalDate.parse(scanner.nextLine().trim());
                break;
            } catch (IllegalArgumentException e) {
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


        CellBook cellBook = new CellBook(nameBook, nameAuthor, publishedData,
                description, price, StatusBookEnum.AVAILABLE);


        addBook(cellBook);
        printStream.println("### Книга добавленна!");
    }


    //todo: несколько экземпляров
    public void changeStatusBook(CellBook cellBook) {
        for (CellBook item : listBook) {
            if (item.equals(cellBook)) {
                item.setStatusBookEnum(StatusBookEnum.AVAILABLE);
                printStream.println("# Статус успешно изменен");
            }
        }

    }


    public CellBook getAnyCellBook() {
        String nameBook;
        String nameAuthor;
        LocalDate publishedData;
        printStream.print("Введите название книги: ");
        nameBook = scanner.nextLine().trim();

        printStream.print("Введите автора книги: ");
        nameAuthor = scanner.nextLine().trim();

        while (true) {
            try {
                printStream.print("Вывведите дату издания: ");
                publishedData = LocalDate.parse(scanner.nextLine().trim());
                break;
            } catch (IllegalArgumentException e) {
                printStream.println("Дата введена неверно попробуйте еще раз!");
            }
        }

        for (CellBook item : listBook) {
            if (Objects.equals(item.getName(), nameBook)
                    && Objects.equals(item.getAuthor(), nameAuthor)
                    && Objects.equals(item.getPublishedData(), publishedData)) {
                return item;
            }
        }
//todo Заюзать Optional
        return null;

    }


    public void changeStatusBook() {

        StatusBookEnum statusBookEnum = null;
        CellBook cellBookTemp = getAnyCellBook();
        if (cellBookTemp == null) {
            printStream.println("# нет такой книги");
            return;
        }


        while (true) {
            printStream.println("Выбирите стутас книги: ");
            printStream.println("[1] Книга доступна");
            printStream.println("[2] Книга недоступна");
            printStream.print("Введите число: ");
            Integer inputNumber;
            while (true) {
                try {
                    inputNumber = Integer.parseInt(scanner.nextLine().trim());
                    break;
                } catch (IllegalArgumentException illegalArgumentException) {
                    printStream.println("Попробуйте еще раз");
                }
            }

            switch (inputNumber) {
                case 1 -> statusBookEnum = StatusBookEnum.AVAILABLE;
                case 2 -> statusBookEnum = StatusBookEnum.NOT_AVAILABLE;
            }
            if (statusBookEnum != null) {
                break;
            }
        }
        cellBookTemp.setStatusBookEnum(statusBookEnum);
        printStream.println("## Статус успешно установленн!");
    }


    //Список залежавшихся книг не проданные больше 6 месяцев
    public List<CellBook> getStaleBook(List<CellBook> books) {
        return books.stream()
                .filter(i -> ChronoUnit.MONTHS.between(i.getLastDeliverDate(), i.getLastSelleDate()) > 6)
                .collect(Collectors.toList());
    }


    public List<CellBook> sortByName(List<CellBook> books) {
        return books.stream()
                .sorted(Comparator.comparing(CellBook::getName).reversed())
                .collect(Collectors.toList());
    }

    public List<CellBook> sorByPrice(List<CellBook> books) {
        return books.stream()
                .sorted(Comparator.comparing(CellBook::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<CellBook> sortByDate(List<CellBook> books) {
        return books.stream()
                .sorted(Comparator.comparing(CellBook::getPublishedData).reversed())
                .collect(Collectors.toList());
    }

    public List<CellBook> sortByStatus(List<CellBook> books) {
        return books.stream()
                .sorted(Comparator.comparing(CellBook::getStatusBookEnum).reversed())
                .collect(Collectors.toList());
    }

}
