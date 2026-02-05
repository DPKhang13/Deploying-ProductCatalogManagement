package com.example.project.exception.product;

public class DuplicateProductException extends  RuntimeException{
    public DuplicateProductException(String message) {
        super(message);
    }
}
