package org.example;

public interface ConstantsPath {
    String EXPORT_FILE_BOOK = "src/main/resources/export/exportBooks.csv";
    String IMPORT_FILE_BOOK = "src/main/resources/import/importBooks.csv";

    String EXPORT_FILE_ORDER = "src/main/resources/export/exportOrders.csv";
    String IMPORT_FILE_ORDER = "src/main/resources/import/importOrders.csv";

    String EXPORT_FILE_REQUEST_BOOK = "src/main/resources/export/exportRequests.csv";
    String IMPORT_FILE_REQUEST_BOOK = "src/main/resources/import/importRequests.csv";


    String BOOK_TITLE = "id;name;author;publishedData;description;price;amount;statusBookEnum;references;lastDeliverDate;lastSelleDate\n";
    String ORDER_TITLE = "ID:createDate:completedDate:amountSum:orderStatusEnum:bookId_N\n";
    String REQUEST_TITLE = "IDRequest:IdBook:status\n";

}
