package com.edusol.retailbanking.application.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String msg)
    {
        super(msg);
    }
}
