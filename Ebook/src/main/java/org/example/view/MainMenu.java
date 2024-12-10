package org.example.view;

public class MainMenu implements Menu {
    @Override
    public void showMenu() {
        printStream.println("==============================");
        printStream.println("=======    Main menu   =======");
        printStream.println("==============================");
        printStream.println("[1] Работа с книгами");
        printStream.println("[2] Работа с заказами");
        printStream.println("[3] Работа с запросами");
    }
}
