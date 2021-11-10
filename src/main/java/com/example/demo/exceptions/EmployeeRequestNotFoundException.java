package com.example.demo.exceptions;

public class EmployeeRequestNotFoundException extends RuntimeException{
    public EmployeeRequestNotFoundException(int id)  {
        super("Could not find request for employee " + id);
    }
}
