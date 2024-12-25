package org.example.model;

import org.example.ConstantsPath;
import org.example.model.exception.NoClearFileException;
import org.example.model.request.RequestBook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface SrvFileManager extends ConstantsPath {

    String DEFAULT_DELIMITER = ",";

    PrintStream printStream = System.out;


    void importModel(Long id);

    // void exportAll();

    void importAll();

    //----------------- Экспортировка всех элементов Book, Order, RequestBook -----------------------
    default <T extends Item> void exportAll(final String path, final String title, List<T> list) {
        if (!clearFile(path)) {
            printStream.println("### Ошибка очистки файла файлами");
            return;
        }
        try {
            writeTitle(path, title);
            for (T t : list) {
                t.writeDate(path);
            }
        } catch (RuntimeException e) {
            printStream.println("### Запись не произошла! ");
            return;
        }
        printStream.println("### Успешно все записалось в файл!");
    }


    //----------------- Экспортировка по Id у элементов Book, Order, RequestBook -----------------------
    default <T extends Item> void exportItem(Long id, final String path, final String title, List<T> list) {
        if (!clearFile(path)) {
            throw new NoClearFileException("### Ошибка очистки файла файла!");
        }

        Optional<T> optionalBook = findById(id, list);
        if (optionalBook.isPresent()) {
            T item = optionalBook.get();
            if (item.writeTitle(EXPORT_FILE_ORDER, title) &&
                item.writeDate(EXPORT_FILE_ORDER)) {
                printStream.printf("### Успешно экспортирована книга id = %d\n", id);
                return;
            }
        }
        printStream.println("### Такой книги нет!");
    }


    //----------------- Поиск по Id у элементов Book, Order, RequestBook -----------------------
    default <T extends Item> Optional<T> findById(Long id, List<T> list) {
        for (T item : list) {
            if(Objects.equals(item.getId(), id)){
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }


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


    default boolean writeTitle(String strPath, String title) {
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
            writer.write(title); // Используем BufferedWriter
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }
}
