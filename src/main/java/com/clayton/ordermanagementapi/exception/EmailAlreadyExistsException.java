package com.clayton.ordermanagementapi.exception;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(){
        super("Email already exists");
    }
}
