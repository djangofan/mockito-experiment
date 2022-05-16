package com.test.meta.util;

public class MetaException extends RuntimeException {
    public MetaException(String message) {
        super(message);
    }
    public MetaException(String message, Throwable e) {
        super(message, e);
    }
}
