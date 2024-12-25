package org.example.view;

import java.io.PrintStream;
import java.util.List;

public interface Menu {
    PrintStream printStream = System.out;

    void showMenu();

    default void showTypeInfoList() {
    }

    default void showErrorInput() {
        printStream.println("Неверный ввод! Попробуйте еще раз!");
    }


    default void printListObject(List<? extends Object> list) {
        if (list.size() == 0) {
            printStream.println("### В массиве пусто");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            printStream.println("{" + (i + 1) + "} " + list.get(i));
        }
    }
}
