package com.mmf.financeflow.exception;

public class DuplicatedAccountException extends RuntimeException {
    public DuplicatedAccountException(String message) {
        super(message);
    }
}
