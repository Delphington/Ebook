package org.example.view;

public class BookMenu implements Menu {
    @Override
    public void showMenu() {
        printStream.println("==============================");
        printStream.println("========  Book menu  =========");
        printStream.println("==============================");
        printStream.println("[1] Добавить еще один экземпляр книги");
        printStream.println("[2] Списать книгу со склада");
        printStream.println("[3] Вывести список книг");
        printStream.println("[4] Экспортировать книгу");
        printStream.println("[5] Экспортировать все книги");
        printStream.println("[6] Импортировать книгу");
        printStream.println("[7] Импортировать все книги");
        printStream.println("[8] Вернуться в главное меню");
        printStream.println("[9] Выйти из программы");
    }

    @Override
    public void showTypeInfoList() {
        printStream.println("--------------------------------------------------------------");
        printStream.println("[1] Вывести список книг библиотеки (сортировка по алфавиту)");
        printStream.println("[2] Вывести список книг библиотеки (сортировка по дате издания)");
        printStream.println("[3] Вывести список книг библиотеки (сортировка по цене)");
        printStream.println("[4] Вывести список книг библиотеки (сортировка по количество)");
        printStream.println("[5] Вывести список залежавшихся книг (сортировка по дате поступления)");
        printStream.println("[6] Вывести список залежавшихся книг (сортировка по цене)");

    }
}