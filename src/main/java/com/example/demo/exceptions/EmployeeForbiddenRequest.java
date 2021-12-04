package com.example.demo.exceptions;

public class EmployeeForbiddenRequest extends RuntimeException{
    public EmployeeForbiddenRequest(String error)  {
        super(error);
    }
}
