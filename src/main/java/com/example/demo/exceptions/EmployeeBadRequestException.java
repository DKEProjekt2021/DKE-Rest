package com.example.demo.exceptions;

public class EmployeeBadRequestException extends RuntimeException {

    public EmployeeBadRequestException(String error)  {
        super(error);
    }
}
