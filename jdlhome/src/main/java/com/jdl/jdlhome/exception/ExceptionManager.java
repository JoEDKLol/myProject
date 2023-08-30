package com.jdl.jdlhome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice
@RestController
public class ExceptionManager {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException e){

        //System.out.println("ExceptionHandler::" + e.getErrorCode().name());

        //return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorCode().name() + " " + e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorCode().name());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
