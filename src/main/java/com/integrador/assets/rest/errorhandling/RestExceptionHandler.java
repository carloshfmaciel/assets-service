package com.integrador.assets.rest.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.integrador.assets.exception.BadRequestException;
import com.integrador.assets.exception.InvalidParameterException;
import com.integrador.assets.exception.NotFoundException;
import com.integrador.assets.exception.UnexpectedException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(), BadRequestException.class.getName(), ex.getMessage()));
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(), InvalidParameterException.class.getName(), ex.getMessage()));
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ErrorResponse(
				HttpStatus.NOT_FOUND.value(), NotFoundException.class.getName(), ex.getMessage()));
	}

	@ExceptionHandler(UnexpectedException.class)
	public ResponseEntity<ErrorResponse> handleUnexpectedException(UnexpectedException ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), UnexpectedException.class.getName(), ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), Exception.class.getName(), ex.getMessage()));
	}

}
