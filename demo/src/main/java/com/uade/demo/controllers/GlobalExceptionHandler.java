package com.uade.demo.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(YourCustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Cambia el código de estado según sea necesario
    @ResponseBody
    public ResponseEntity<String> handleYourCustomException(YourCustomException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Mensaje de error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        // Mensaje de error genérico
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error interno del servidor: " + ex.getMessage());
    }

}
