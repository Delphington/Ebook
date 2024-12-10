package org.example.view;

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
}
