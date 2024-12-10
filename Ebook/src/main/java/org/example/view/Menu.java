package org.example.view;

import java.io.PrintStream;
import java.util.List;

public interface Menu {
    PrintStream printStream = System.out;
    void showMenu();
    default void showTypeInfoList() {}
    default void showErrorInput(){
        printStream.println("Неверный ввод! Попробуйте еще раз!");
    }
    default void printListObject(List<? extends Object> V){

    };
}
