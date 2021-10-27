package com.mercadolibre.challenge.exception;

public class InvalidDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidDataException(String customMessage) {
        super(customMessage);
    }

}
