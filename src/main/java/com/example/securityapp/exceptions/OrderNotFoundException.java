package com.example.securityapp.exceptions;

public class OrderNotFoundException extends RuntimeException{
    private static final long serialVerisionUID = 1;

    public OrderNotFoundException(String message) {
        super(message);
    }
}
