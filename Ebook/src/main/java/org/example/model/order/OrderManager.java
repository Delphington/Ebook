package org.example.model.order;

import lombok.Getter;
import lombok.ToString;
import org.example.model.*;
import org.example.model.book.Book;
import org.example.model.request.RequestBook;
import org.example.model.request.RequestBookManager;
import org.example.model.request.RequestBookStatus;

import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@ToString
@Getter
public class OrderManager implements SrvFileManager {

    private List<Order> orderList;
    private RequestBookManager requestBookManager;
    private static final PrintStream printStream = System.out;
    private static final Scanner scanner = new Scanner(System.in);

    public OrderManager(RequestBookManager requestBookManager) {
        orderList = new ArrayList<>();
        this.requestBookManager = requestBookManager;
    }


    //Обновляем реквесты добавление новой книги
    public boolean updateRequestList(Book book) {
        for (RequestBook requestBook : RequestBook.requestBookList) {
            if (requestBook.getBook().equals(book)) {
                requestBook.setRequestBookStatus(RequestBookStatus.CLOSED);
                book.decrementReferences();
                System.out.println("Был изменен одина книга");
                updateOrderList();
                return true;
            }
        }
        return false;
    }


    public void updateOrderList() {
        for (Order item : orderList) {
            int sizeList = item.getBookListInOrder().size();
            int cnt = 0;
            for (Book bookItem : item.getBookListInOrder()) {
                if (bookItem.getAmount() > 0) {
                    cnt++;
                }
            }
            if (cnt == sizeList) {
                item.setOrderStatusEnum(StatusOrderEnum.DONE);
            }
        }
    }


    public void createOrder() {
        orderList.add(new Order());
        System.out.println("### Заказ создан!");
    }

    public void cancelOrder(Order order) {
        if (order.getOrderStatusEnum() == StatusOrderEnum.DONE) {
            System.out.println("### Заказ завершен, отменить невозможно!");
        } else {
            order.setOrderStatusEnum(StatusOrderEnum.CANCEL);
            //Закрываем все запросы на книги
            for (Book book : order.getBookListInOrder()) {
                if (book.getAmount() == 0) {
                    requestBookManager.closeRequest(book);
                }
            }
        }


        for (Order item : orderList) {
            if (item.equals(order)) {
                order.cancelOrder();
                System.out.println("Заказ отменент!");
                return;
            }
        }
        System.out.println("Не найден заказ!");
    }


    public void completedOrder(Order order) {
        for (Order item : orderList) {
            if (item.equals(order)) {
                item.completedOrder();
                return;
            }
        }
        System.out.println("Не найден заказ!");
    }


    //Меняем статус книги
    public void changeStatusOrder(Order order, StatusOrderEnum statusOrderEnum) {
        if (order.getOrderStatusEnum() == statusOrderEnum
                || statusOrderEnum == StatusOrderEnum.NEW) {
            System.out.println("### Такой статус не имеет смысла присваивать");
            return;
        }
        //Книга в статусе new - то есть книга в заказ
        //и присваиваем Done cancel
        if (order.getOrderStatusEnum() == StatusOrderEnum.NEW) {
            //Закрываем все запросы
            for (Book itemBook : order.getBookListInOrder()) {
                if (itemBook.getAmount() == 0) {
                    requestBookManager.closeRequest(itemBook);
                }
            }
        }
        //Это для любого статусу
        order.setOrderStatusEnum(statusOrderEnum);
        System.out.println("### Cтатус установленн");
    }


    public Double calculateCompletedOrderProfit() {
        double sum = 0;
        for (Order item : orderList) {
            if (item.getOrderStatusEnum() == StatusOrderEnum.DONE) {
                sum += item.getAmountSum();
            }
        }
        return sum;
    }




    public List<Order> sortByCompletedOrdersBetweenDates(List<Order> orderList, LocalDate from, LocalDate to){
        return orderList.stream()
                .filter(order -> order.getOrderStatusEnum() == StatusOrderEnum.DONE)
                .filter(order -> order.getCompletedDate().isAfter(from) && order.getCompletedDate().isBefore(to))
                .sorted(Comparator.comparing(Order::getCompletedDate))
                .toList();


    }




    public Integer getSelectedOrderIndex() {
        printStream.println("Выбирите какой по счету заказ: ");
        if (orderList.size() == 0) {
            printStream.println("### нет заказов");
        }

        for (int i = 0; i < orderList.size(); i++) {
            printStream.println("[" + (i + 1) + "] " + orderList.get(i));
        }
        Integer number;
        while (true) {
            try {
                printStream.print("Введите число: ");
                String line = scanner.nextLine().trim();
                number = Integer.parseInt(line) - 1;
                if (number >= 0 && orderList.size() > number) {
                    break;
                }
                printStream.println("Неверный ввод! Попробуйте еще раз!");
            } catch (IllegalArgumentException e) {
                printStream.println("Неверный ввод! Попробуйте еще раз!");
            }
        }
        return number;
    }


    //Количество выполненных заказов
    public List<Order> getCompletedOrder(List<Order> orderList) {
        return orderList.stream().filter(order -> order.getOrderStatusEnum() == StatusOrderEnum.DONE)
                .collect(Collectors.toList());
    }

    //Выбор книг по статусу(приыбли не прибыли)
    public List<Order> getOrderListByStatus(StatusOrderEnum status) {
        return orderList.stream().filter(order -> order.getOrderStatusEnum().equals(status))
                .collect(Collectors.toList());
    }


    public List<Order> sortByAmount(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getAmountSum).reversed())
                .collect(Collectors.toList());

    }

    public List<Order> sortByCreateDate(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreateDate).reversed())
                .collect(Collectors.toList());
    }


    public List<Order> sortByCompletedDate(List<Order> orders) {
        return orders.stream()
                .filter(i -> i.getOrderStatusEnum() == StatusOrderEnum.DONE) //Filter for DONE status
                .sorted(Comparator.comparing(Order::getCreateDate).reversed())
                .collect(Collectors.toList());
    }


    public List<Order> sortByStatus(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderStatusEnum).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void writeToFile() {
        if (!clearFile(FILE_TO_WRITE_ORDER)) {
            printStream.println("### Ошибка очистки файла файлами");
            return;
        }

        for (int i = 0; i < orderList.size(); i++) {
            try {
                orderList.get(i).writeDate(FILE_TO_WRITE_ORDER);
            } catch (RuntimeException | IOException e) {
                printStream.println("### Запись не произошла! ");
                return;
            }
        }
        printStream.println("### Успешно все записалось в файл!");
    }
}
