package org.example;

public class RequestBookManager {

    public RequestBookManager(){

    }


//    public void addRequestBook(RequestBook requestBook) {
//         RequestBook.requestBookList.add(requestBook);
//    }

    public void createRequest(){

    }

    public static void closeRequest(Book book) {
        for (RequestBook requestBook : RequestBook.requestBookList) {
            if (requestBook.getBook().equalsBook(book)) {
                requestBook.setRequestBookStatus(RequestBookStatus.CLOSED);
            }
        }
    }

}
