package com.example.demo.exceptions;

public class EmployeeIDNotFoundException extends RuntimeException{
    public EmployeeIDNotFoundException(String errormessage, int id)  {
        super(errormessage + id);
    }

}
