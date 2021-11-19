package com.example.demo.exceptions;

public class TargetIDBadRequestException extends RuntimeException{

    public TargetIDBadRequestException(String error)  {
        super(error);
    }

}
