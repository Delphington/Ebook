package org.example;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Manager {
    private CellBookManager bookManager;
    private OrderManager orderManager;

    public Manager(CellBookManager bookManager, OrderManager orderManager) {
        this.bookManager = bookManager;
        this.orderManager = orderManager;
    }


    public void changeBookStatus(CellBook cellBook) {
        if (bookManager.checkExistBook(cellBook)) {
            bookManager.changeStatusBook(cellBook);
            orderManager.updateOrderList(cellBook);

        } else {
            bookManager.addBook(cellBook);
        }
    }
}
