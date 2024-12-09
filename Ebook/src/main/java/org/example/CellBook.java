package org.example;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter

//todo: book можно вынести как отдельную сущность
public class CellBook extends Book {

    private String description;


    @Override
    public String toString() {
        return "CellBook{" +
                "name=" + super.getName() +
                ", author='" + super.getAuthor() +
                ", publishedData=" + super.getPublishedData() +
                ", description='" + description +
                ", price=" + price +
                ", references=" + references +
                ", statusBookEnum=" + statusBookEnum +
                ", lastDeliverDate=" + lastDeliverDate +
                ", lastSelleDate=" + lastSelleDate +
                '}' + '\n';
    }

    private Double price;

    private Integer references = 0; //Сколько раз заказывали книгу
    private StatusBookEnum statusBookEnum; //В наличие или не в наличии
    private LocalDate lastDeliverDate;
    private LocalDate lastSelleDate;

    public CellBook(String name, String author, LocalDate publishedData, String description, Double price, StatusBookEnum statusBookEnum) {
        super(name, author, publishedData);
        this.price = price;
        this.description = description;
        this.statusBookEnum = statusBookEnum;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        CellBook bookCell = (CellBook) object;
        return Objects.equals(description, bookCell.description) && Objects.equals(price, bookCell.price) && Objects.equals(references, bookCell.references) && statusBookEnum == bookCell.statusBookEnum && Objects.equals(lastDeliverDate, bookCell.lastDeliverDate) && Objects.equals(lastSelleDate, bookCell.lastSelleDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, price, references, statusBookEnum, lastDeliverDate, lastSelleDate);
    }
}
