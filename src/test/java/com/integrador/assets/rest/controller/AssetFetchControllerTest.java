package com.integrador.assets.rest.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.integrador.assets.domain.Asset;
import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.rest.response.AssetResponse;
import com.integrador.assets.rest.validator.AssetFetchValidator;
import com.integrador.assets.service.AssetFetchService;

@DisplayName("Writing assertions to AssetFetchController")
@SuppressWarnings("rawtypes")
public class AssetFetchControllerTest {

	@InjectMocks
	private AssetFetchController assetFetchController;

	@Mock
	private AssetFetchService assetFetchService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCallGetByFiltersMethod() {
		try (MockedStatic<AssetFetchValidator> mockStatic = mockStatic(AssetFetchValidator.class)) {

			mockStatic.when(() -> {
				AssetFetchValidator.validate(mock(AssetFetchRequest.class));
			}).then(invocationOnMock -> null);

			when(assetFetchService.findByFilters(any())).thenReturn(mock(AssetResponse.class));

			ResponseEntity response = assetFetchController.getByFilters(null, null, null, 1, 30);

			assertNotNull(response);
			mockStatic.verify(() -> AssetFetchValidator.validate(any(AssetFetchRequest.class)), times(1));
		}
	}
	
	@Test
	void testCallGetByIdMethod() {
		
		Asset mockAsset = mock(Asset.class);
		
		when(assetFetchService.findById(anyString())).thenReturn(mockAsset);
		when(mockAsset.toMap()).thenReturn(Map.of());
		
		ResponseEntity response = assetFetchController.getById("123");
		
		assertNotNull(response);
	}
}
