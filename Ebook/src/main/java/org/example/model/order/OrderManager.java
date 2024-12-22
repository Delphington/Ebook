package org.example.model.order;

import lombok.Getter;
import lombok.ToString;
import org.example.model.*;
import org.example.model.book.Book;
import org.example.model.book.BookManager;
import org.example.model.book.StatusBookEnum;
import org.example.model.exception.NoClearFileException;
import org.example.model.request.RequestBook;
import org.example.model.request.RequestBookManager;
import org.example.model.request.RequestBookStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ToString
@Getter
public class OrderManager implements SrvFileManager {

    private List<Order> orderList;
    private RequestBookManager requestBookManager;
    private static final PrintStream printStream = System.out;
    private static final Scanner scanner = new Scanner(System.in);
    private BookManager bookManager;


    public OrderManager(RequestBookManager requestBookManager, BookManager bookManager) {
        orderList = new ArrayList<>();
        this.requestBookManager = requestBookManager;
        this.bookManager = bookManager;
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

    // --------------------- Работа с файлами --------------------------------

    private void addOrder(Order order) {
        orderList.add(order);
    }


    public Optional<Order> findById(Long id) {
        for (Order order : orderList) {
            if (order.getId().equals(id)) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    @Override
    public void exportModel(Long id){
        if (!clearFile(EXPORT_FILE_ORDER)) {
            throw new NoClearFileException("### Ошибка очистки файла файла!");
        }

        Optional<Order> optionalBook = findById(id);
        if (optionalBook.isPresent()) {
            Order order = optionalBook.get();
            if (order.writeTitle(EXPORT_FILE_ORDER, order.generateTitle()) &&
                order.writeDate(EXPORT_FILE_ORDER)) {
                printStream.printf("### Успешно экспортирована книга id = %d\n", id);
                return;
            }
        }
        printStream.println("### Такой книги нет!");
    }


    @Override
    public void exportAll() {
        if (!clearFile(EXPORT_FILE_ORDER)) {
            printStream.println("### Ошибка очистки файла файлами");
            return;
        }

        boolean flag = true;

        for (int i = 0; i < orderList.size(); i++) {
            try {
                if (flag) {
                    String title = orderList.get(i).generateTitle();
                    orderList.get(i).writeTitle(EXPORT_FILE_ORDER, title);
                    flag = false;
                }
                orderList.get(i).writeDate(EXPORT_FILE_ORDER);
            } catch (RuntimeException e) {
                printStream.println("### Запись не произошла! ");
                return;
            }
        }
        printStream.println("### Успешно все записалось в файл!");
    }

    @Override
    public void importAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(IMPORT_FILE_ORDER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Order> optional = getParseOrder(line);
                if (optional.isPresent()) {
                    Order importOrder = optional.get();
                    Optional<Order> optionalOrder = findById(importOrder.getId());
                    if (optionalOrder.isPresent()) {
                        Order item = optionalOrder.get();
                        item.of(importOrder);

//                        System.out.println("****************************************");
//                        System.out.println("в базе: " + item);
//                        System.out.println("Из файла: " + importOrder);
//                        System.out.println("****************************************");
                    } else {
                        addOrder(importOrder);
                    }

                } else {
                    System.out.println("### Заказ не считался");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }


    private Optional<Order> getParseOrder(String input) {
        //ID:createDate:completedDate:amountSum:orderStatusEnum:bookId_N
        Optional<String[]> optional = getParseLine(input);
        if (optional.isPresent()) {
            String[] arr = optional.get();

            System.out.println("%%%: " +Arrays.toString(arr));

            //Минимальное количество для заказа (без книг)
            if (arr.length < 5) {
                return Optional.empty();
            }

            Long id = null;
            LocalDate createDate = null;
            LocalDate completedDate = null;
            Double amountSum = null;
            StatusOrderEnum orderStatusEnum = null;
            try {
                id = Long.parseLong(arr[0]);
                createDate = LocalDate.parse(arr[1]);
                try{
                    completedDate = LocalDate.parse(arr[2]);
                }catch (RuntimeException e){
                  //  System.out.println("### ComplectedDate is NULL");
                }
                amountSum = Double.parseDouble(arr[3]);
                orderStatusEnum = StatusOrderEnum.valueOf(arr[4]);
            } catch (RuntimeException e) {
                System.err.println("Ошибка: преобразование объекта");
                return Optional.empty();
            }

            Order order = new Order(id, createDate, completedDate, amountSum, orderStatusEnum);

            for (int i = 5; i < arr.length; i++) {
                Long bookId = null;
                try {
                    bookId = Long.parseLong(arr[i]);
                } catch (RuntimeException e) {
                    //todo: выкинуть свой Exception
                    System.err.println("### Не верное преобразование айди книги");
                }

                if (bookId != null) {
                    Optional<Book> optionalBook = bookManager.findById(bookId);
                    if (optionalBook.isPresent()) {
                        order.addBook(optionalBook.get());
                    } else {
                        System.err.println("### Такой книги нет в библио");
                    }
                }
            }
            return Optional.of(order);
        }

        return Optional.empty();
    }


    // --------------- Сортировки --------------------------


    public List<Order> sortByCompletedOrdersBetweenDates(List<Order> orderList, LocalDate from, LocalDate to) {
        return orderList.stream()
                .filter(order -> order.getOrderStatusEnum() == StatusOrderEnum.DONE)
                .filter(order -> order.getCompletedDate().isAfter(from) && order.getCompletedDate().isBefore(to))
                .sorted(Comparator.comparing(Order::getCompletedDate))
                .toList();
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
}
