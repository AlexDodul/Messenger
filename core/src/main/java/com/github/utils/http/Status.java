package com.github.utils.http;

public class Status {

    private final int statusCode;

    private final String statusText;

    public Status(int statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

}
