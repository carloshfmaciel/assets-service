package com.integrador.assets.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.integrador.assets.constants.Pagination;
import com.integrador.assets.domain.Asset;
import com.integrador.assets.rest.response.AssetResponse;

@DisplayName("Writing assertions to AssetResponseBuilder")
@ExtendWith(MockitoExtension.class)
public class AssetResponseBuilderTest {

	@Test
	void whenPageNumberIsNullItMustBeSettedWithDefaultPageNumber() {
		List<Asset> assets = new ArrayList<>();

		Integer pageNumber = null;
		Integer pageSize = 10;
		Integer totalResultCount = 1;

		assertEquals(AssetResponseBuilder.toAssetResponse(assets, pageNumber, pageSize, totalResultCount),
				AssetResponse.builder().pageNumber(Pagination.DEFAULT_PAGE_NUMBER).pageSize(pageSize)
						.total(totalResultCount)
						.result(assets.stream().map(JSONObject::toMap).collect(Collectors.toList())).build());
	}
	
	@Test
	void whenPageSizeIsNullItMustBeSettedWithDefaultPageSize() {
		List<Asset> assets = new ArrayList<>();

		Integer pageNumber = 1;
		Integer pageSize = null;
		Integer totalResultCount = 1;

		assertEquals(AssetResponseBuilder.toAssetResponse(assets, pageNumber, pageSize, totalResultCount),
				AssetResponse.builder().pageNumber(pageNumber).pageSize(Pagination.DEFAULT_PAGE_SIZE)
						.total(totalResultCount)
						.result(assets.stream().map(JSONObject::toMap).collect(Collectors.toList())).build());
	}
	
	@Test
	void whenAllParametersAreInformedItMustReturnThese() {
		List<Asset> assets = new ArrayList<>();

		Integer pageNumber = 1;
		Integer pageSize = 10;
		Integer totalResultCount = 1;

		assertEquals(AssetResponseBuilder.toAssetResponse(assets, pageNumber, pageSize, totalResultCount),
				AssetResponse.builder().pageNumber(pageNumber).pageSize(pageSize)
						.total(totalResultCount)
						.result(assets.stream().map(JSONObject::toMap).collect(Collectors.toList())).build());
	}

}
