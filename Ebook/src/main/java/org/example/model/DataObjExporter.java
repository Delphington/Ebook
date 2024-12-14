package org.example.model;

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public interface DataObjExporter {
    String DEFAULT_DELIMITER = ";";


    String generateString();

    default boolean writeDate(String strPath) throws IOException {
        Path path = Paths.get(strPath);
        // Убедимся, что родительская директория существует
        Path parentDir = path.getParent();
        if (parentDir != null) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                System.err.println("Ошибка в директории: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(generateString()); // Используем BufferedWriter
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }
}
