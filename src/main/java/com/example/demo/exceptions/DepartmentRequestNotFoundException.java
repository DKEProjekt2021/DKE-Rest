package com.example.demo.exceptions;

public class DepartmentRequestNotFoundException extends RuntimeException{
    public DepartmentRequestNotFoundException(int id)  {
        super("Could not find request for employee " + id);
    }
}
