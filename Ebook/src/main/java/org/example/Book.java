package org.example;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Book {
    private String name;
    private String author;
    private LocalDate publishedData;
    private Double price;


    private StatusBookEnum statusBookEnum;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book temp = (Book) obj;
        return Objects.equals(name, temp.name) && Objects.equals(author, temp.author)
                && Objects.equals(publishedData, temp.publishedData)
                && Objects.equals(price, temp.price);
    }
}
