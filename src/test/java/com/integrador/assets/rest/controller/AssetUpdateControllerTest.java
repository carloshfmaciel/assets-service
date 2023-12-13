package com.integrador.assets.rest.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.integrador.assets.service.AssetUpdateService;

@DisplayName("Writing assertions to AssetUpdateController")
public class AssetUpdateControllerTest {

	@InjectMocks
	private AssetUpdateController assetUpdateController;

	@Mock
	private AssetUpdateService assetUpdateService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCallImportFromManuSisMethod() {
		ResponseEntity response = assetUpdateController.importFromManuSis();

		assertNotNull(response);
		verify(assetUpdateService, times(1)).update();
	}

}
