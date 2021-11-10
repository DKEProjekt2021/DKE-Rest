package com.example.demo.advices;

import com.example.demo.exceptions.DepartmentBadRequestException;
import com.example.demo.exceptions.EmployeeBadRequestException;
import com.example.demo.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class DepartmentBadRequestAdvice {

    @ExceptionHandler(DepartmentBadRequestException.class)
    public ResponseEntity<ExceptionResponse> customException(DepartmentBadRequestException ex) {
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode("BAD_REQUEST");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }

}