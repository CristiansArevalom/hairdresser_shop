package com.hairsalon.exception;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

/*@RestControllerAdvice is the combination of both @ControllerAdvice and @ResponseBody: */

/**
 * Manejador de excepciones para la clase {@link ModelNotFoundException}.
 * Captura las excepciones y devuelve una respuesta HTTP con un cuerpo de
 * respuesta personalizado.
 */
@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler {


    /**
     * Maneja las excepciones de tipo {@code ModelNotFoundException} y devuelve una
     * respuesta HTTP 404 con un cuerpo de respuesta personalizado.
     *
     * @param ex      La excepción de tipo {@code ModelNotFoundException}.
     * @param request La solicitud web que provocó la excepción.
     * @return Una entidad ResponseEntity que contiene un cuerpo de respuesta
     *         personalizado y el código de estado HTTP 404.
     */
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleModelNotFoundException(ModelNotFoundException ex,
            WebRequest request) {
        // Crear un objeto CustomErrorResponse con la información de la excepción
        CustomErrorResponse err = new CustomErrorResponse(
                "/errors/resource-not-found",
                "Resource Not Found",
                404,
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now());
        // Devolver una ResponseEntity con el objeto CustomErrorResponse y el código de
        // estado HTTP 404
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja las excepciones de tipo {@code SQLException} y devuelve una respuesta
     * HTTP 409 con un cuerpo de respuesta personalizado.
     *
     * @param ex      La excepción de tipo {@code SQLException}.
     * @param request La solicitud web que provocó la excepción.
     * @return Una entidad ResponseEntity que contiene un cuerpo de respuesta
     *         personalizado y el código de estado HTTP 409.
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<CustomErrorResponse> handleSQLException(SQLException ex, WebRequest request) {
        // Crear un objeto CustomErrorResponse con la información de la excepción
        CustomErrorResponse err = new CustomErrorResponse(
                "/errors/resource-not-found",
                "SQL Exception",
                409,
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now());
        // Devolver una ResponseEntity con el objeto CustomErrorResponse y el código de
        // estado HTTP 409
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

        /**
     * Maneja las excepciones de tipo {@code DataIntegrityViolationException} y devuelve una
     * respuesta HTTP 409 con un cuerpo de respuesta personalizado.
     *
     * @param ex      La excepción de tipo {@code DataIntegrityViolationException}.
     * @param request La solicitud web que provocó la excepción.
     * @return Una entidad ResponseEntity que contiene un cuerpo de respuesta
     *         personalizado y el código de estado HTTP 409.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(
                "/errors/data-integrity-violation",
                "DAO : Data Integrity Violation",
                409,
                ex.getLocalizedMessage(),
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

        /**
     * Maneja las excepciones de tipo {@code MethodArgumentNotValidException} y devuelve una
     * respuesta HTTP 400 con un cuerpo de respuesta personalizado.
     *
     * @param ex La excepción de tipo {@code MethodArgumentNotValidException}.
     * @return Una entidad ResponseEntity que contiene un cuerpo de respuesta
     *         personalizado y el código de estado HTTP 400.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(
                "/errors/data-integrity-violation",
                "BAD REQUEST : Validation failed for argument",
                409,
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    
    /**
     * Maneja todas las excepciones no gestionadas y devuelve una respuesta HTTP 500
     * con un cuerpo de respuesta personalizado.
     *
     * @param ex      La excepción no gestionada.
     * @param request La solicitud web que provocó la excepción.
     * @return Una entidad ResponseEntity que contiene un cuerpo de respuesta
     *         personalizado y el código de estado HTTP 500.
     */
    /* */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllException(Exception ex, WebRequest request) {
        // Crear un objeto CustomErrorResponse con la información de la excepción
        CustomErrorResponse err = new CustomErrorResponse(
                "/errors/unknown-exception",
                "Unhandled exception",
                500,
                ex.getClass()+": "+ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now());
        // Devolver una ResponseEntity con el objeto CustomErrorResponse y el código de
        // estado HTTP 500

        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
