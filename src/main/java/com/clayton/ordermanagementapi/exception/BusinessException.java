package com.clayton.ordermanagementapi.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String message){
        super(message);
    }

}
