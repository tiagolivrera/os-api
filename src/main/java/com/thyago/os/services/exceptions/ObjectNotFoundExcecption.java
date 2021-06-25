package com.thyago.os.services.exceptions;

public class ObjectNotFoundExcecption extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundExcecption(String message) {
        super(message);
    }

    public ObjectNotFoundExcecption(String message, Throwable cause) {
        super(message, cause);
    }

}
