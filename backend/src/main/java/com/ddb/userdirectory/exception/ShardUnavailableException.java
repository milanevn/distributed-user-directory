package com.ddb.userdirectory.exception;

public class ShardUnavailableException extends RuntimeException {

    public ShardUnavailableException(String message) {
        super(message);
    }
}
