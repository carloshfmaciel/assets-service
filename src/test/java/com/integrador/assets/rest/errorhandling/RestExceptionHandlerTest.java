package com.integrador.assets.rest.errorhandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.integrador.assets.exception.BadRequestException;
import com.integrador.assets.exception.InvalidParameterException;
import com.integrador.assets.exception.NotFoundException;
import com.integrador.assets.exception.UnexpectedException;

import jakarta.servlet.http.HttpServletRequest;

@DisplayName("Writing assertions to RestExceptionHandler")
public class RestExceptionHandlerTest {

	@InjectMocks
	private RestExceptionHandler restExceptionHandler;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCallHandleBadRequestExceptionMethod() {
		ResponseEntity<ErrorResponse> response = restExceptionHandler
				.handleBadRequestException(new BadRequestException(), mock(HttpServletRequest.class));

		assertNotNull(response);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof ErrorResponse);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
		assertTrue(response.getBody().getException().equals(BadRequestException.class.getName()));
	}

	@Test
	void testCallHandleInvalidParameterExceptionMethod() {
		ResponseEntity<ErrorResponse> response = restExceptionHandler.handleInvalidParameterException(
				new InvalidParameterException("some error message"), mock(HttpServletRequest.class));

		assertNotNull(response);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof ErrorResponse);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
		assertTrue(response.getBody().getException().equals(InvalidParameterException.class.getName()));
	}
	
	@Test
	void testCallHandleNotFoundExceptionMethod() {
		ResponseEntity<ErrorResponse> response = restExceptionHandler.handleNotFoundException(
				new NotFoundException(), mock(HttpServletRequest.class));

		assertNotNull(response);
		assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof ErrorResponse);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatusCode());
		assertTrue(response.getBody().getException().equals(NotFoundException.class.getName()));
	}
	
	@Test
	void testCallHandleUnexpectedExceptionMethod() {
		ResponseEntity<ErrorResponse> response = restExceptionHandler.handleUnexpectedException(
				new UnexpectedException(new Exception()), mock(HttpServletRequest.class));

		assertNotNull(response);
		assertTrue(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof ErrorResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatusCode());
		assertTrue(response.getBody().getException().equals(UnexpectedException.class.getName()));
	}

	@Test
	void testCallHandleExceptionMethod() {
		ResponseEntity<ErrorResponse> response = restExceptionHandler.handleException(
				new Exception(), mock(HttpServletRequest.class));

		assertNotNull(response);
		assertTrue(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof ErrorResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatusCode());
		assertTrue(response.getBody().getException().equals(Exception.class.getName()));
	}
	
}
