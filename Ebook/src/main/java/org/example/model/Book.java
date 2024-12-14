package org.example.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
public class Book implements DataObjExporter {
    private int ID;

    private final String name;
    private final String author;
    private final LocalDate publishedData;
    private String description;
    private Double price;

    private Integer amount = 1;
    private StatusBookEnum statusBookEnum = StatusBookEnum.AVAILABLE; //В наличие или не в наличии
    private Integer references = 0; //Сколько раз заказывали книгу
    private LocalDate lastDeliverDate;
    private LocalDate lastSelleDate;


    private static int counterID = 1;

    //конструтор для обчного создания
    public Book(String name, String author, LocalDate publishedData, String description, Double price, Integer amount) {
        this.name = name;
        this.author = author;
        this.publishedData = publishedData;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.lastDeliverDate = LocalDate.now();
        ID = counterID;
        counterID++;
    }


    //Конструктор для парсинга
    public Book(int ID, String name, String author, LocalDate publishedData,
                String description, Double price, Integer amount,
                StatusBookEnum statusBookEnum, Integer references,
                LocalDate lastDeliverDate, LocalDate lastSelleDate) {
        this.ID = ID;
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
        return ID == ((Book) object).getID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, publishedData, description, price, references, statusBookEnum, lastDeliverDate, lastSelleDate);
    }

    @Override
    public String toString() {
        return "Book{" +
               "ID='" + ID + '\'' +
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


    @Override
    public String generateString() {
        StringBuilder temp = new StringBuilder();
        //    ID:name:author:publishedData:description:price:amount:statusBookEnum:references:lastDeliverDate:lastSelleDate;
        temp.append(ID).append(DEFAULT_DELIMITER);
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
