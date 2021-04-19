package com.github.utils.http;

public class StatusFactory {

    public static Status getStatus(int statusCode, String statusText){
        return new Status(statusCode, statusText);
    }

}
