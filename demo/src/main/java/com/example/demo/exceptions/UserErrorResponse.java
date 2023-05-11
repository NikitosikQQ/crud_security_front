package com.example.demo.exceptions;

public class UserErrorResponse {
    private String message;

    public UserErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
