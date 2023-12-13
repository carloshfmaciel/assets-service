package com.integrador.assets.rest.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.integrador.assets.constants.Pagination;

@DisplayName("Writing assertions to AssetFetchRequest")
public class AssetFetchRequestTest {

	@Test
	void testCallGetPageNumberMethodWhenPageNumberIsZeroItMustReturnZero() {
		AssetFetchRequest assetFetchRequest = new AssetFetchRequest();
		assetFetchRequest.setPageNumber(0);

		assertEquals(0, assetFetchRequest.getPageNumber());
	}

	@Test
	void testCallGetPageNumberMethodWhenPageNumberIsHigherThanZeroItMustReturnTheNumberMinusOne() {
		AssetFetchRequest assetFetchRequest = new AssetFetchRequest();

		int pageNumber = 3;
		assetFetchRequest.setPageNumber(pageNumber);

		assertEquals(pageNumber - 1, assetFetchRequest.getPageNumber());
	}

	@Test
	void testCallGetPageNumberMethodWhenPageNumberIsNullItMustReturnZero() {
		AssetFetchRequest assetFetchRequest = new AssetFetchRequest();
		assetFetchRequest.setPageNumber(null);

		assertEquals(0, assetFetchRequest.getPageNumber());
	}

	@Test
	void testCallGetPageNumberMethodWhenPageSizeIsNullItMustReturnThirty() {
		AssetFetchRequest assetFetchRequest = new AssetFetchRequest();
		assetFetchRequest.setPageSize(null);

		assertEquals(Pagination.DEFAULT_PAGE_SIZE, assetFetchRequest.getPageSize());
	}

	@Test
	void testCallGetPageNumberMethodWhenPageSizeIsNotNullItMustReturnPageSize() {
		AssetFetchRequest assetFetchRequest = new AssetFetchRequest();

		int pageSize = 10;
		assetFetchRequest.setPageSize(pageSize);

		assertEquals(pageSize, assetFetchRequest.getPageSize());
	}

	@Test
	void testCallGetOrderByWhenOrderByIsNullItMustReturnDefaultOrderBy() {
		AssetFetchRequest assetFetchRequest = new AssetFetchRequest();

		assertEquals(Pagination.DEFAULT_ORDER_BY, assetFetchRequest.getOrderBy());
	}

	@Test
	void testCallGetOrderByWhenOrderByIsInformedItMustReturnJustOrderBy() {
		AssetFetchRequest assetFetchRequest = new AssetFetchRequest();

		String orderBy = "name:asc";
		assetFetchRequest.setOrderBy(orderBy);

		assertEquals(orderBy, assetFetchRequest.getOrderBy());
	}

}
