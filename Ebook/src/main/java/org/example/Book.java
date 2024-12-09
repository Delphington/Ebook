package org.example;

import lombok.*;

import java.time.LocalDate;


@EqualsAndHashCode
@Setter
@Getter
@AllArgsConstructor
public class Book {
    private final String name;
    private final String author;
    private final LocalDate publishedData;
}
