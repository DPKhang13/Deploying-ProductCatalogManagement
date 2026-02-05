package com.example.project.exception.product;

public class InvalidProductDataException extends  RuntimeException{
    public InvalidProductDataException(String message) {
        super(message);
    }
}
