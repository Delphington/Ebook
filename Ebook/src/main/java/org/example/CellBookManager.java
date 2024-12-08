package org.example;


import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;

@ToString
public class CellBookManager {
    @Getter
    private List<CellBook> listBook;


    public CellBookManager(List<CellBook> listBook) {
        this.listBook = listBook;
    }


    public void deleteBook(CellBook book){
        listBook.remove(book);
    }


    public void addBook(CellBook book){
        listBook.add(book);
    }

    public void setStatusBook(CellBook book) {
        book.setStatusBookEnum(StatusBookEnum.NOT_AVAILABLE);
    }


    //Список залежавшихся книг не проданные больше 6 месяцев
    public List<CellBook> getStaleBook(List<CellBook> books){
        return books.stream()
                .filter(i -> ChronoUnit.MONTHS.between(i.getLastDeliverDate(), i.getLastSelleDate()) > 6)
                .collect(Collectors.toList());
    }


    public List<CellBook> sortByName(List<CellBook> books) {
        return books.stream()
                .sorted(Comparator.comparing(CellBook::getName).reversed())
                .collect(Collectors.toList());
    }

    public List<CellBook> sorByPrice(List<CellBook> books) {
        return books.stream()
                .sorted(Comparator.comparing(CellBook::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<CellBook> sortByDate(List<CellBook> books) {
        return books.stream()
                .sorted(Comparator.comparing(CellBook::getPublishedData).reversed())
                .collect(Collectors.toList());
    }

    public List<CellBook> sortByStatus(List<CellBook> books) {
        return books.stream()
                .sorted(Comparator.comparing(CellBook::getStatusBookEnum).reversed())
                .collect(Collectors.toList());
    }

}
