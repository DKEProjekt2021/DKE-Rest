package com.example.demo.exceptions;

public class DepartmentBadRequestException extends RuntimeException {

    public DepartmentBadRequestException(String error)  {
        super(error);
    }
}
