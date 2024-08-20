package com.dardanqsot.eval.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static <T extends ApiException> Supplier<T> supplier(Function<String, T> fn, String message) {
        return () -> {
            throw fn.apply(message);
        };
    }

}