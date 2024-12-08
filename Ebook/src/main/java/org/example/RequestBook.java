package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestBook {
    private CellBook cellBook;
    private RequestBookStatus requestBookStatus;
}
