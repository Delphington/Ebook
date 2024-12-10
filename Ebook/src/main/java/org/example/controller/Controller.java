package org.example.controller;

import org.example.Book;
import org.example.Order;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public interface Controller {
    Scanner scanner = new Scanner(System.in);
    PrintStream printStream = System.out;

    ActionType input();

    void showMenu();

    ActionType run();

    default Integer parseStringToInteger() {
        Integer temp;
        while (true) {
            try {
                printStream.print("Сделайте выбор: ");
                String nextLine = scanner.nextLine().trim();
                temp = Integer.parseInt(nextLine);
                break;
            } catch (IllegalArgumentException e) {
                printStream.println("Неверный выбор! Попробуйте еще раз");
            }
        }
        return temp;
    }



    default Integer getIndexChooseBook(List<Book> books) {
        printStream.println("Выбирите какой по счету книгу для заказа: ");
        for (int i = 0; i < books.size(); i++) {
            printStream.println("[" + (i + 1) + "] " + books.get(i));
        }

        Integer number;
        while (true) {
            try {
                printStream.print("Введите число: ");
                String line = scanner.nextLine().trim();
                number = Integer.parseInt(line) - 1;
                if (number >= 0 && books.size() > number) {
                    break;
                }
                printStream.println("Неверно! Попробуй еще раз ввести число: ");
            } catch (IllegalArgumentException e) {
                printStream.println("Попробуй еще раз ввести число: ");
            }
        }
        return number;
    }



}
