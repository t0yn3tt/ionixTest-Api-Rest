package com.edgardo.ionixtest.dto;
import java.util.Date;

public class ErrorDetails {
    private Date time;
    private String message;
    private String details;

    public ErrorDetails(Date time, String message, String details) {
        this.time = time;
        this.message = message;
        this.details = details;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
