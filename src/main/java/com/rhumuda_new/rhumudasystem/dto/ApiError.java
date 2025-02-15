package com.rhumuda_new.rhumudasystem.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private List<String> errors;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
        this.errors = new ArrayList<>();
    }

    public ApiError(int status, String message, List<String> errors) {
        this();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(int status, String message, String error) {
        this();
        this.status = status;
        this.message = message;
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
