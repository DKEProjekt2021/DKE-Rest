package com.example.demo.advices;

import com.example.demo.exceptions.EmployeeBadRequestException;
import com.example.demo.exceptions.EmployeeForbiddenRequest;
import com.example.demo.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class EmployeeForbiddenRequestAdvice {

    @ExceptionHandler(EmployeeForbiddenRequest.class)
    public ResponseEntity<ExceptionResponse> customException(EmployeeForbiddenRequest ex) {
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode("FORBIDDEN");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.FORBIDDEN);
    }
}
