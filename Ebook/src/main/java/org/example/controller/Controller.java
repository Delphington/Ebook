package org.example.controller;

import java.io.PrintStream;
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

}
