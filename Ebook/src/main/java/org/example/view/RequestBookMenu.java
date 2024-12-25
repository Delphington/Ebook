package org.example.view;

public class RequestBookMenu implements Menu {
    @Override
    public void showMenu() {
        printStream.println("==============================");
        printStream.println("=======  Request menu  =======");
        printStream.println("==============================");
        printStream.println("[1] Оставить запрос на книгу");
        printStream.println("[2] Вывести список запросов на книги (сортировка по количеству запросов)");
        printStream.println("[3] Вывести список запросов на книги (сортировка по цене)");
        printStream.println("[4] Экспортировать запрос");
        printStream.println("[5] Экспортировать все запросы");
        printStream.println("[6] Импортировать запрос");
        printStream.println("[7] Импортировать все запросы");
        printStream.println("[8] Вернуться в главное меню");
        printStream.println("[9] Выйти из программы");
    }
}
