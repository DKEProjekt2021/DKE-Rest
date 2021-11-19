package com.example.demo.exceptions;

public class TargetIDNotFoundException extends RuntimeException{
    public TargetIDNotFoundException(String request)  {
        super(request);
    }
}
