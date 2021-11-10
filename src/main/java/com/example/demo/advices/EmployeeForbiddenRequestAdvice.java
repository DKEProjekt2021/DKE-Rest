package com.example.demo.advices;

import com.example.demo.exceptions.EmployeeBadRequestException;
import com.example.demo.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class EmployeeForbiddenRequestAdvice {

    @ExceptionHandler(EmployeeBadRequestException.class)
    public ResponseEntity<ExceptionResponse> customException(EmployeeBadRequestException ex) {
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode("FORBIDDEN");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.FORBIDDEN);
    }
}
