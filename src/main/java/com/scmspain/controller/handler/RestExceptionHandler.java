package com.scmspain.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Generic rest exception handler. Converts Java exception to Json ErrorResponses.
 *
 * @author ricardmore
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Here we catch both IllegalArgumentException and NullPointer <b>assuming</b> all the NullPointer are managed by the code
     * or using Objects.requireNonNull
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { IllegalArgumentException.class, NullPointerException.class })
    protected ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse( HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Default catch method. Generic server error.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<ErrorResponse> handleGenericException(RuntimeException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse( HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
