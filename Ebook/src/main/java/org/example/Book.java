package org.example;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter

public class Book {
    private final String name;
    private final String author;
    private final LocalDate publishedData;
    private String description;
    private Double price;

    private Integer amount = 1;
    private StatusBookEnum statusBookEnum; //В наличие или не в наличии
    private Integer references = 0; //Сколько раз заказывали книгу
    private LocalDate lastDeliverDate;
    private LocalDate lastSelleDate;


    public Book(String name, String author, LocalDate publishedData, String description, Double price, StatusBookEnum statusBookEnum) {
        this.name = name;
        this.author = author;
        this.publishedData = publishedData;
        this.price = price;
        this.description = description;
        this.statusBookEnum = statusBookEnum;
        this.lastDeliverDate  = LocalDate.now();
    }

    public boolean equalsBook(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Book book = (Book) object;
        return Objects.equals(name, book.getName())
                && Objects.equals(publishedData, book.publishedData)
                && Objects.equals(description, book.description)
                && Objects.equals(price, price);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Book book = (Book) object;
        return Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(publishedData, book.publishedData) && Objects.equals(description, book.description) && Objects.equals(price, book.price) && Objects.equals(references, book.references) && statusBookEnum == book.statusBookEnum && Objects.equals(lastDeliverDate, book.lastDeliverDate) && Objects.equals(lastSelleDate, book.lastSelleDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, publishedData, description, price, references, statusBookEnum, lastDeliverDate, lastSelleDate);
    }

    public void incrementAmount() {
        amount++;
    }

    public void decrementAmount() {
        amount--;
    }
}
