package com.example.demo.advices;

import com.example.demo.exceptions.EmployeeRequestNotFoundException;
import com.example.demo.exceptions.TargetIDNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TargetIDNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TargetIDNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String targetIDNotFoundAdviceHandler(TargetIDNotFoundException ex) {
        return ex.getMessage();
    }

}
