package org.example;

import lombok.experimental.UtilityClass;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

@UtilityClass
public class UtilsInput {
    private final static Scanner scanner = new Scanner(System.in);
    private final static PrintStream printStream = System.out;

    public static LocalDate inputDateFromConsole() {
        int year;
        short month;
        short day;
        while (true) {
            printStream.print("Введите год: ");
            try {
                year = Integer.parseInt(scanner.nextLine().trim());
                if (year > 0 && year < 3000) {
                    break;
                }
                printStream.println("Ошибка, не попали в верный промежуток!");
            } catch (RuntimeException e) {
                printStream.println("Ошибка, попробуйте еще раз!");
            }
        }

        while (true) {
            printStream.print("Введите месяц: ");
            try {
                month = Short.parseShort(scanner.nextLine().trim());
                if (month > 0 && month < 13) {
                    break;
                }
                printStream.println("Ошибка, не попали в верный промежуток!");
            } catch (RuntimeException e) {
                printStream.println("Ошибка, попробуйте еще раз!");
            }
        }
        while (true) {
            printStream.print("Введите день: ");
            try {
                day = Short.parseShort(scanner.nextLine().trim());
                if (day > 0 && day < 32) {
                    break;
                }
                printStream.println("Ошибка, не попали в верный промежуток!");
            } catch (RuntimeException e) {
                printStream.println("Ошибка, попробуйте еще раз!");
            }
        }
        return LocalDate.of(year, month, day);
    }


    public Integer getIntegerFromConsole() {
        int tempInput;
        while (true) {
            try {
                printStream.print("Сделайте выбор: ");
                String nextLine = scanner.nextLine().trim();
                tempInput = Integer.parseInt(nextLine);
                break;
            } catch (IllegalArgumentException e) {
                printStream.println("Неверный выбор! Попробуйте еще раз");
            }
        }
        return tempInput;
    }
}
