package org.example.view;

import java.util.List;

public class RequestBookMenu implements Menu {
    @Override
    public void showMenu() {
        printStream.println("==============================");
        printStream.println("=======  Request menu  =======");
        printStream.println("==============================");
        printStream.println("[1] Оставить запрос на книгу");
        printStream.println("[2] Вывести список запросов на книги (сортировка по количеству запросов)");
        printStream.println("[3] Вывести список запросов на книги (сортировка по цене)");
        printStream.println("[4] Вернуться в главное меню");
        printStream.println("[5] Выйти из программы");
    }

    @Override
    public void printListObject(List<? extends Object> requests) {
        if(requests.size() ==0 ){
            printStream.println("### В массиве пусто");
            return;
        }
        for (int i = 0; i < requests.size(); i++) {
            printStream.println("{" + (i + 1) + "} " + requests.get(i));
        }
    }
}
