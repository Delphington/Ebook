package org.example.model;

import org.example.ConstantsPath;
import org.example.model.exception.NoClearFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public interface SrvFileManager extends ConstantsPath {

    String DEFAULT_DELIMITER = ",";

    PrintStream printStream = System.out;

    default void exportModel(Long id) throws NoClearFileException {
    }

    default void importModel(Long id) {
    }

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

    default void printAllFile(final String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
