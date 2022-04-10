package com.edgardo.ionixtest.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public AppException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AppException(HttpStatus status, String message, String message1) {
        this.status = status;
        this.message = message;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
