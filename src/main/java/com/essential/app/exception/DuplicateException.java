package com.essential.app.exception;

public class DuplicateException
extends Exception {
    private static final long serialVersionUID = -5542627657418159227L;

    public DuplicateException(Exception e) {
        super(e);
    }

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Exception e) {
        super(message, e);
    }

    public DuplicateException() {
    }
}

