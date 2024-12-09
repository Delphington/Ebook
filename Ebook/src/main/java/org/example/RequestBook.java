package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestBook {
    private Book book;
    private RequestBookStatus requestBookStatus;
}
