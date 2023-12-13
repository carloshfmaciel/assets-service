package com.integrador.assets.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Writing assertions to BadRequestException")
public class BadRequestExceptionTest {
	
	@Test
	void testCallDefaultConstructor() {
		BadRequestException badRequestException = new BadRequestException();
		assertNotNull(badRequestException);
	}

}
