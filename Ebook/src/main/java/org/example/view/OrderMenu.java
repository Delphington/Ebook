package org.example.view;

import java.util.List;

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
        printStream.println("[7] Посмотреть детали заказа");
        printStream.println("[8] Вывести сумму заработанных средств за период времени");
        printStream.println("[9] Вывести список заказов");

        printStream.println("[10] Экспортировать заказ");
        printStream.println("[11] Экспортировать все заказы");
        printStream.println("[12] Импортировать заказ");
        printStream.println("[13] Импортировать все заказы");



        printStream.println("[14] Вернуться в главное меню");
        printStream.println("[15] Выйти из программы");
        printStream.print("Введите число: ");
    }


    @Override
    public void showTypeInfoList() {
        printStream.println("[1] Вывести список заказов (сортировка по дате исполнения)");
        printStream.println("[2] Вывести список заказов (сортировка по цене)");
        printStream.println("[3] Вывести список заказов (сортировка по статусу)");


        //Нужно оттестить
        printStream.println("[4] Вывести список выполненных заказов за период времени(сортировка по дате)");
        printStream.println("[5] Вывести список выполненных заказов за период времени(сортировка по цене)");
        printStream.println("[6] Вывести количество выполненных заказов за период времени");
    }

    //todo: можно будто бы в интерфейс вынести и делать .super
    @Override
    public void printListObject(List<? extends Object> orders) {
        if(orders.size() ==0 ){
            printStream.println("### В массиве пусто");
            return;
        }
        for (int i = 0; i < orders.size(); i++) {
            printStream.println("{" + (i + 1) + "} " + orders.get(i));
        }
    }
}