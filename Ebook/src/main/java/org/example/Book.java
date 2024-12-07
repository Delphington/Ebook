package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Book {
    private String name;
    private StatusBookEnum statusBookEnum;
}
