package com.SpringEduManager.web.exceptions;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Proporciona respuestas JSON consistentes para diferentes tipos de errores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de campos (@Valid, @Validated).
     * Ocurre cuando los datos de entrada no cumplen las reglas de validación.
     * @param ex Excepción con los detalles de validación
     * @return ResponseEntity con detalles de los campos que fallaron la validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                    fieldError -> fieldError.getField(),
                    fieldError -> "Datos no válidos" //fieldError.getDefaultMessage()
                ));
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Validatción fallida", "detalles", errors));
    }

    /**
     * Maneja excepciones de tiempo de ejecución (lógica de negocio).
     * Ocurre cuando hay errores controlados en el código.
     * @param ex RuntimeException con el mensaje de error
     * @return ResponseEntity con el mensaje de error
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Maneja excepciones genéricas no controladas.
     * Ocurre cuando hay errores inesperados en la aplicación.
     * @param ex Exception genérica
     * @return ResponseEntity con mensaje de error interno
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Ocurrió un error interno: " + ex.getMessage()));
    }
}
