package com.example.project.exception.category;

public class UnauthorizedException  extends  RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
