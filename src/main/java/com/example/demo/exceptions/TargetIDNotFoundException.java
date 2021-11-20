package com.example.demo.exceptions;

public class TargetIDNotFoundException extends RuntimeException{
    public TargetIDNotFoundException(String request, int targetstateid)  {
        super(request);
    }
}
