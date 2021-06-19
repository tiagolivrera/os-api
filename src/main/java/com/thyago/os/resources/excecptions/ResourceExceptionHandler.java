package com.thyago.os.resources.excecptions;

import com.thyago.os.services.exceptions.DataIntegrityViolationException;
import com.thyago.os.services.exceptions.ObjectNotFoundExcecption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundExcecption.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundExcecption e) {
        StandardError error = new StandardError(System.currentTimeMillis(), 
                                                HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e) {
        StandardError error = new StandardError(System.currentTimeMillis(), 
                                                HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
}
