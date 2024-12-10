package org.example.view;

public class OrderMenu implements Menu{
    @Override
    public void showMenu() {
        printStream.println("==============================");
        printStream.println("=======  Order menu  =======");
        printStream.println("==============================");
        printStream.println("Выбирите действие с заказами: ");
        printStream.println("[1] Создать заказ");
        printStream.println("[2] Отменить заказ"); //отменить
        printStream.println("[3] Добавить книгу в заказ");
        printStream.println("[4] Вывести все заказы");
        printStream.println("[5] Вывести книги, на которых запрос");
        printStream.println("[6] Изменить статус заказа");
        //---
        printStream.println("[7] Вывести сумму заработанных средств за период времени");
        printStream.println("[14] Вернуться в главное меню");
        printStream.println("[15] Выйти из программы");
        printStream.print("Введите число: ");
    }
}
