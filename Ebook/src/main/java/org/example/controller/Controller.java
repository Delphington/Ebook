package org.example.controller;

import org.example.model.book.Book;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public interface Controller {
    Scanner scanner = new Scanner(System.in);
    PrintStream printStream = System.out;

    ActionType input();

    ActionType run();


    default Long getIdChooseBook(Map<Long, Book> mapa) {
        printStream.println("Выбирите id книгу для заказа: ");
        for (Map.Entry<Long, Book> item : mapa.entrySet()) {
            printStream.println("[" + item.getKey() + "] " + item.getValue());

        }
        Long id;
        while (true) {
            try {
                printStream.print("Введите число: ");
                String line = scanner.nextLine().trim();
                id = Long.parseLong(line);
                if (mapa.get(id) != null) {
                    break;
                }
                printStream.println("Неверно! Попробуй еще раз ввести число: ");
            } catch (IllegalArgumentException e) {
                printStream.println("Попробуй еще раз ввести число: ");
            }
        }
        return id;
    }


    default Long getIdChoose(List<? extends Object> list) {
        printStream.println("Выбирите id книгу для заказа: ");
        for (int i = 0; i < list.size(); i++) {
            printStream.println(list.get(i));

        }
        Long id;
        while (true) {
            try {
                printStream.print("Введите число: ");
                String line = scanner.nextLine().trim();
                id = Long.parseLong(line);
                return id;
            } catch (IllegalArgumentException e) {
                printStream.println("Попробуй еще раз ввести число: ");
            }
        }
    }


    default Long getScannerNumber() {
        Long id;
        while (true) {
            try {
                printStream.print("Введите число ID: ");
                String line = scanner.nextLine().trim();
                id = Long.parseLong(line);
                break;

            } catch (IllegalArgumentException e) {
                printStream.println("Попробуй еще раз ввести число: ");
            }
        }
        return id;
    }


    default Integer getIndexChoose(List<? extends Object> list) {
        printStream.println("Введите число: ");
        for (int i = 0; i < list.size(); i++) {
            printStream.println("[" + (i + 1) + "] " + list.get(i));
        }
        Integer index;
        while (true) {
            try {
                printStream.print("Введите число: ");
                String line = scanner.nextLine().trim();
                index = Integer.parseInt(line) - 1;
                if (index >= 0 && index < list.size()) {
                    break;
                }
                printStream.println("Неверно! Попробуй еще раз ввести число: ");
            } catch (IllegalArgumentException e) {
                printStream.println("Попробуй еще раз ввести число: ");
            }
        }
        return index;

    }


}