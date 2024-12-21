package org.example.model.exception;

import java.io.IOException;

public class NoClearFileException extends RuntimeException {

    public NoClearFileException(String message) {
        super(message);
    }
}
