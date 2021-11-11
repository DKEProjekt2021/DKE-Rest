package com.example.demo.exceptions;

public class EmployeeRequestNotFoundException extends RuntimeException{
    public EmployeeRequestNotFoundException(String request)  {
        super(request);
    }
}
