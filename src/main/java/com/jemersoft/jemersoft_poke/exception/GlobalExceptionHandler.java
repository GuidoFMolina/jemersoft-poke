package com.jemersoft.jemersoft_poke.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the Pokémon API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles Pokémon not found exceptions.
     */
    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePokemonNotFound(PokemonNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles Pokémon already exists exceptions.
     */
    @ExceptionHandler(PokemonAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handlePokemonAlreadyExists(PokemonAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Handles validation errors for request DTOs.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> error.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Handles all other exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Unexpected error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
