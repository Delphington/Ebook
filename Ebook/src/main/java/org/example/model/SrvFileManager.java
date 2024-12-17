package org.example.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Optional;

public interface SrvFileManager {
    String EXPORT_FILE_BOOK = "src/main/resources/export/exportBooks.csv";
    String IMPORT_FILE_BOOK = "src/main/resources/import/importBooks.csv";

    String FILE_TO_WRITE_ORDER = "src/main/resources/export/exportOrders.csv";

    String DEFAULT_DELIMITER = "=";


    void writeToFile();


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
