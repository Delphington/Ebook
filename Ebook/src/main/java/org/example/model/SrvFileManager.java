package org.example.model;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public interface SrvFileManager {
    String EXPORT_FILE_BOOK = "src/main/resources/export/exportBooks.csv";
    String IMPORT_FILE_BOOK = "src/main/resources/import/importBooks.csv";

    String EXPORT_FILE_ORDER = "src/main/resources/export/exportOrders.csv";
    String IMPORT_FILE_ORDER = "src/main/resources/import/importOrders.csv";

    String EXPORT_FILE_REQUEST_BOOK = "src/main/resources/export/exportRequests.csv";
    String IMPORT_FILE_REQUEST_BOOK = "src/main/resources/import/importRequests.csv";

    String DEFAULT_DELIMITER = ";";

    PrintStream printStream = System.out;

    void exportAll();
    void importAll();

    default boolean clearFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING).close();
            return true;
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
            return false;
        }
    }

    default Optional<String[]> getParseLine(String input) {
        if (input == null || input.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(input.split(DEFAULT_DELIMITER));
    }
}
