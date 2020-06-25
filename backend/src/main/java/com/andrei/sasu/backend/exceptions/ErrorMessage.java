package com.andrei.sasu.backend.exceptions;

public class ErrorMessage {
    private String message;
    private Long timestamp;
    private int statusCode;
    private String statusReason;

    public ErrorMessage(String message, Long timestamp, int statusCode, String statusReason) {
        this.message = message;
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.statusReason = statusReason;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }
}
