package com.example.demo.advices;

import com.example.demo.exceptions.EmployeeIDNotFoundException;
import com.example.demo.exceptions.EmployeeRequestNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeIDNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(EmployeeIDNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String uppercaseRequestNotFoundHandler(EmployeeIDNotFoundException ex) {
        return ex.getMessage();
    }
}

