package com.integrador.assets.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Writing assertions to InvalidParameterException")
public class InvalidParameterExceptionTest {

	@Test
	void testCallConstructorPassingStringAsParameter() {
		InvalidParameterException invalidParameterException = new InvalidParameterException("some message");
		assertNotNull(invalidParameterException);
	}
}
