package com.integrador.assets.rest.request.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import com.integrador.assets.rest.request.AssetFetchRequest;

@DisplayName("Writing assertions to AssetFetchRequestParser")
public class AssetFetchRequestParserTest {

	@Test
	void testGetFiltersMethodWhenParameterIsNullItMustReturnNull() {
		Criteria result = AssetFetchRequestParser.getFilters(null);
		assertNull(result);
	}

	@Test
	void testGetFiltersMethodWhenParameterFilterIsNullItMustReturnNull() {
		AssetFetchRequest request = AssetFetchRequest.builder().filter(null).build();
		Criteria result = AssetFetchRequestParser.getFilters(request);
		assertNull(result);
	}

	@Test
	void testGetFiltersMethodWhenParameterFilterInformedIsStringItMustReturnCriteriaObject() {
		AssetFetchRequest request = AssetFetchRequest.builder().filter("name:maciel").build();
		Criteria result = AssetFetchRequestParser.getFilters(request);
		assertNotNull(result);
	}

	@Test
	void testGetFiltersMethodWhenParameterFilterInformedIsBooleanItMustReturnCriteriaObject() {
		AssetFetchRequest request = AssetFetchRequest.builder().filter("active:true").build();
		Criteria result = AssetFetchRequestParser.getFilters(request);
		assertNotNull(result);

		request = AssetFetchRequest.builder().filter("active:false").build();
		result = AssetFetchRequestParser.getFilters(request);
		assertNotNull(result);
	}

	@Test
	void testGetFiltersMethodWhenParameterFilterInformedIsNumericItMustReturnCriteriaObject() {
		AssetFetchRequest request = AssetFetchRequest.builder().filter("id:123").build();
		Criteria result = AssetFetchRequestParser.getFilters(request);
		assertNotNull(result);
	}

	@Test
	void testGetFieldsMethodWhenFieldIsInformedAsStringItMustReturnStringList() {
		AssetFetchRequest request = AssetFetchRequest.builder().fields("id;name;salary").build();
		List<String> fields = AssetFetchRequestParser.getFields(request);
		assertFalse(CollectionUtils.isEmpty(fields));
		assertEquals(3, fields.size());
	}

	@Test
	void testGetOrderByMethodWhenFieldsAreInformedAsStringItMustReturnSortObject() {
		AssetFetchRequest request = AssetFetchRequest.builder().orderBy("name:asc;salary:desc").build();
		Sort orderBy = AssetFetchRequestParser.getOrderBy(request);
		assertNotNull(orderBy);
	}

	@Test
	void testGetPaginationMethodWhenPaginationIsInformedAsParameterItMustReturnPageableObject() {
		AssetFetchRequest request = AssetFetchRequest.builder().pageNumber(1).pageSize(30).build();
		Pageable pagination = AssetFetchRequestParser.getPagination(request);
		assertNotNull(pagination);
		assertEquals(1 - 1, pagination.getPageNumber());
		assertEquals(30, pagination.getPageSize());
	}

	@Test
	void testisASCMethodWhenInformedSomeNullParameterItMustThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			AssetFetchRequestParser.isASC(null);
		});
	}

}
