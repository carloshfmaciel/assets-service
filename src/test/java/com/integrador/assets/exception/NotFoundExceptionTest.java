package com.integrador.assets.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Writing assertions to NotFoundException")
public class NotFoundExceptionTest {
	
	@Test
	void testCallDefaultConstructor() {
		NotFoundException notFoundException = new NotFoundException();
		assertNotNull(notFoundException);
	}

}
