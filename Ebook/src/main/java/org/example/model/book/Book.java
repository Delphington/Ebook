package org.example.model.book;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Item;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class Book implements Item {
    private final Long id;
    private String name;
    private String author;
    private LocalDate publishedData;
    private String description;
    private Double price;

    private Integer amount = 1;
    private StatusBookEnum statusBookEnum = StatusBookEnum.AVAILABLE; //В наличие или не в наличии
    private Integer references = 0; //Сколько раз заказывали книгу
    private LocalDate lastDeliverDate;
    private LocalDate lastSelleDate;


    private static long counterID = 1;

    //конструтор для обчного создания
    public Book(String name, String author, LocalDate publishedData, String description, Double price, Integer amount) {
        this.name = name;
        this.author = author;
        this.publishedData = publishedData;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.lastDeliverDate = LocalDate.now();
        id = counterID;
        counterID++;
    }


    //Конструктор для парсинга
    public Book(Long id, String name, String author, LocalDate publishedData,
                String description, Double price, Integer amount,
                StatusBookEnum statusBookEnum, Integer references,
                LocalDate lastDeliverDate, LocalDate lastSelleDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishedData = publishedData;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.statusBookEnum = statusBookEnum;
        this.references = references;
        this.lastDeliverDate = lastDeliverDate;
        this.lastSelleDate = lastSelleDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Book bookTemp = (Book) object;
        return Objects.equals(id, bookTemp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
               "ID='" + id + '\'' +
               "name='" + name + '\'' +
               ", author='" + author + '\'' +
               ", publishedData=" + publishedData +
               ", description='" + description + '\'' +
               ", price=" + price +
               ", amount=" + amount +
               ", statusBookEnum=" + statusBookEnum +
               ", references=" + references +
               ", lastDeliverDate=" + lastDeliverDate +
               ", lastSelleDate=" + lastSelleDate +
               '}';
    }

    public void incrementAmount() {
        amount++;
    }

    public void decrementAmount() {
        amount--;
    }

    public void incrementReferences() {
        references++;
    }

    public void decrementReferences() {
        references--;
    }



    public void copyOf(Book book){
        this.name = book.name;
        this.author = book.author;
        this.publishedData = book.publishedData;
        this.description = book.description;
        this.price = book.price;
        this.amount = book.amount;
        this.references = book.references;
        this.lastDeliverDate = book.lastDeliverDate;
        this.lastSelleDate = book.lastSelleDate;
    }


    //-------------------------- Для работы с файлами -------------------------------

    @Override
    public String generateInfoObject() {
        StringBuilder temp = new StringBuilder();
        temp.append(id).append(DEFAULT_DELIMITER);
        temp.append(name).append(DEFAULT_DELIMITER);
        temp.append(author).append(DEFAULT_DELIMITER);
        temp.append(publishedData).append(DEFAULT_DELIMITER);
        temp.append(description).append(DEFAULT_DELIMITER);
        temp.append(price).append(DEFAULT_DELIMITER);
        temp.append(amount).append(DEFAULT_DELIMITER);
        temp.append(statusBookEnum).append(DEFAULT_DELIMITER);
        temp.append(references).append(DEFAULT_DELIMITER);
        temp.append(lastDeliverDate).append(DEFAULT_DELIMITER);
        temp.append(lastSelleDate).append("\n");
        return temp.toString();
    }
}
