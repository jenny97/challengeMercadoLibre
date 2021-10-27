package com.mercadolibre.challenge.config;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mercadolibre.challenge.exception.ApiError;
import com.mercadolibre.challenge.exception.InvalidDataException;

@ControllerAdvice
public class InterceptorException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiError> processSupportedExceptions(Exception ex) {

		return new ResponseEntity<>(ApiError.builder()
			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.error(ex.getClass().getSimpleName())
			.message(ex.getMessage())
			.build(), 
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ApiError> invalidDataException(InvalidDataException ex) {
        return createResponseEntityFromException(ex, HttpStatus.BAD_REQUEST);
    }

	private ResponseEntity<ApiError> createResponseEntityFromException(RuntimeException ex, HttpStatus status) {
		Objects.requireNonNull(ex);
		return new ResponseEntity<>(ApiError.builder()
			.status(status.value())
			.error(ex.getClass().getSimpleName())
			.message(ex.getMessage())
			.build(), 
			status);
	}	
}