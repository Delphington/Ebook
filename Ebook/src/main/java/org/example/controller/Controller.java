package org.example.controller;

import org.example.model.Book;
import org.example.view.Menu;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public interface Controller {
    Scanner scanner = new Scanner(System.in);
    PrintStream printStream = System.out;

    ActionType input();

    ActionType run();


    default Integer getIndexChooseBook(List<Book> books) {
        printStream.println("------------------------------------------");
        printStream.println("Выбирите какой по счету книгу для заказа: ");
        for (int i = 0; i < books.size(); i++) {
            printStream.println("[" + (i + 1) + "] " + books.get(i));
        }

        int number;
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
