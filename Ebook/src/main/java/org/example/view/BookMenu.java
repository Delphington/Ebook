package org.example.view;

import java.util.List;

public class BookMenu implements Menu {
    @Override
    public void showMenu() {
        printStream.println("==============================");
        printStream.println("=======  Book menu  =======");
        printStream.println("==============================");
        printStream.println("[1] Добавить еще один экземпляр книги");
        printStream.println("[2] Списать книгу со склада");
        printStream.println("[3] Вывести список книг");
        printStream.println("[4] Вернуться в главное меню");
        printStream.println("[5] Выйти из программы");
    }

    @Override
    public void showTypeInfoList() {
        printStream.println("[1] Вывести список книг библиотеки (сортировка по алфавиту)");
        printStream.println("[2] Вывести список книг библиотеки (сортировка по дате издания)");
        printStream.println("[3] Вывести список книг библиотеки (сортировка по цене)");
        printStream.println("[4] Вывести список книг библиотеки (сортировка по статусу)");
        printStream.println("[5] Вывести список залежавшихся книг (сортировка по дате поступления)");
    }


    @Override
    public void printListObject(List<? extends Object> book) {
        for (int i = 0; i < book.size(); i++) {
            printStream.println("{" + (i + 1) + "} " + book.get(i));
        }
    }
}
