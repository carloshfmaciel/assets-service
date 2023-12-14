package com.integrador.assets.rest.request.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.integrador.assets.exception.InvalidParameterException;
import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.rest.validator.AssetFetchValidator;

@DisplayName("Writing assertions to AssetFetchValidator")
class AssetFetchValidatorTest {
	
	@Test
	void testValidateMethodWhenNoFieldIsInformedItMustNotThrowAnyException() {
		assertDoesNotThrow(() -> {
			AssetFetchValidator.validate(AssetFetchRequest.builder().build());
		});
	}
	
	@Test
	void testValidateMethodWhenFilterIsInvalidItMustThrowInvalidParameterException() {
		assertThrows(InvalidParameterException.class, () -> {
			String invalidFilter = "field=value";
			AssetFetchValidator.validate(AssetFetchRequest.builder().filter(invalidFilter).build());
		});
	}

	@Test
	void testValidateMethodWhenFilterIsValidItMustNotThrowAnyException() {
		assertDoesNotThrow(() -> {
			String validFilter = "field:value";
			AssetFetchValidator.validate(AssetFetchRequest.builder().filter(validFilter).build());
		});
	}
	
	@Test
	void testValidateMethodWhenPageNumberIsLessThanZeroItMustThrowInvalidParameterException() {
		assertThrows(InvalidParameterException.class, () -> {
			int pageNumber = -10;
			AssetFetchValidator.validate(AssetFetchRequest.builder().pageNumber(pageNumber).build());
		});
	}
	
	@Test
	void testValidateMethodWhenPageSizeIsLessThanOneItMustThrowInvalidParameterException() {
		assertThrows(InvalidParameterException.class, () -> {
			int pageSize = 0;
			AssetFetchValidator.validate(AssetFetchRequest.builder().pageSize(pageSize).build());
		});
	}
	
	@Test
	void testValidateMethodWhenPageSizeIsHigherThanFiftyItMustThrowInvalidParameterException() {
		assertThrows(InvalidParameterException.class, () -> {
			int pageSize = 51;
			AssetFetchValidator.validate(AssetFetchRequest.builder().pageSize(pageSize).build());
		});
	}
	
	@Test
	void testValidateMethodWhenOrderByInformedIsInvalidItMustThrowInvalidParameterException() {
		assertThrows(InvalidParameterException.class, () -> {
			String orderBy = "field:someValue";
			AssetFetchValidator.validate(AssetFetchRequest.builder().orderBy(orderBy).build());
		});
	}
	
	@Test
	void testValidateMethodWhenOrderByIsValidItMustNotThrowAnyException() {
		assertDoesNotThrow(() -> {
			String orderBy = "field:asc";
			AssetFetchValidator.validate(AssetFetchRequest.builder().orderBy(orderBy).build());
		});
	}
	
	@Test
	void testValidateMethodWhenOrderByIsNullItMustNotThrowAnyException() {
		assertDoesNotThrow(() -> {
			String orderBy = "";
			AssetFetchValidator.validate(AssetFetchRequest.builder().orderBy(orderBy).build());
		});
	}
}
