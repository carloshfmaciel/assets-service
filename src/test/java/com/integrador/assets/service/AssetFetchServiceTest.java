package com.integrador.assets.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.integrador.assets.domain.Asset;
import com.integrador.assets.mongo.repository.AssetRepository;
import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.rest.response.AssetResponse;

@DisplayName("Writing assertions to AssetFetchService")
public class AssetFetchServiceTest {

	@InjectMocks
	private AssetFetchService assetFetchService;

	@Mock
	private AssetRepository assetRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void whenCallFindByFiltersInformingAllParametersItMustReturnAssetResponse() {
		
		when(assetRepository.findByFilters(any(), any(), any(), any()))
			.thenReturn(List.of(new Asset("{\"id\": 123}"), 
								new Asset("{\"id\": 321}")));
		
		when(assetRepository.countByFilters(any())).thenReturn(2);
		
		AssetResponse response = assetFetchService.findByFilters(AssetFetchRequest.builder().filter("company_id:76").fields("id").build());
		
		assertNotNull(response);
		assertEquals(2, response.getTotal());
		
		verify(assetRepository, times(1)).findByFilters(any(), any(), any(), any());
		verify(assetRepository, times(1)).countByFilters(any());
	}

	@Test
	void whenCallFindByIdItMustReturnAssetObject() {
		
		when(assetRepository.findById(any())).thenReturn(new Asset("{\"id\": 123}"));
				
		Asset asset = assetFetchService.findById("123");
		
		assertNotNull(asset);
		assertEquals("123", asset.get("id").toString());
		
		verify(assetRepository, times(1)).findById(any());
	}

}
