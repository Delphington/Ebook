package org.example.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public interface Item {
    Long getId();

    String DEFAULT_DELIMITER = ",";

    String generateInfoObject();


    default boolean writeToFile(String strPath, String title) {
        Path path = Paths.get(strPath);

        // Убедимся, что родительская директория существует
        Path parentDir = path.getParent();
        if (parentDir != null) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                System.err.println("### Ошибка в директории: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        boolean flag = title == null;


        try (BufferedWriter writer = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (flag) {
                writer.write(generateInfoObject()); // Используем BufferedWriter
            } else {
                writer.write(title);
            }
        } catch (IOException e) {
            System.err.println("### Ошибка при записи в файл: " + e.getMessage());
            return false;
        }
        return true;
    }
}
