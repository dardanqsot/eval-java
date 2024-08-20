package com.dardanqsot.eval.exception;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class NotFoundApiException extends ApiException {

    public NotFoundApiException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundApiException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND);
    }

    public static Supplier<NotFoundApiException> supplier(String message) {
        return () -> { throw new NotFoundApiException(message); };
    }
}
