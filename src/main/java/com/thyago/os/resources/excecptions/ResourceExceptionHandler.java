package com.thyago.os.resources.excecptions;

import com.thyago.os.services.exceptions.DataIntegrityViolationException;
import com.thyago.os.services.exceptions.ObjectNotFoundExcecption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // tratando da excecção lançada quando recebe parametros incorretos atraves da API
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationError error = new ValidationError(System.currentTimeMillis(), 
                                    HttpStatus.BAD_REQUEST.value(), "Erro na validação dos campos!");
        
        // a exceção lançada por MethodArgument... mostra uma lista de erros para cada campo recebido inválido.
        // usando FieldMessage, a saida de erros sai formatada
        for(FieldError x : e.getBindingResult().getFieldErrors()) {
            error.addError(x.getField(), x.getDefaultMessage());
        }  
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);        
    }    
}
