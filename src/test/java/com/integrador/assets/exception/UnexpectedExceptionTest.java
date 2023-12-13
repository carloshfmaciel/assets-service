package com.integrador.assets.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Writing assertions to UnexpectedException")
public class UnexpectedExceptionTest {

	@Test
	void testCallConstructorPassingExceptionAsParameter() {
		UnexpectedException unexpectedException = new UnexpectedException(mock(Exception.class));
		assertNotNull(unexpectedException);
	}

}
