package com.integrador.assets.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.integrador.assets.client.api.ManuSisApiClient;
import com.integrador.assets.exception.UnexpectedException;
import com.integrador.assets.mongo.repository.AssetRepository;
import com.integrador.assets.pubsub.producer.ManuSisProducer;

@DisplayName("Writing assertions to AssetUpdateService")
public class AssetUpdateServiceTest {

	@InjectMocks
	private AssetUpdateService assetUpdateService;

	@Mock
	private ManuSisApiClient manuSisApiClient;

	@Mock
	private AssetRepository assetsRepository;

	@Mock
	private ManuSisProducer manuSisProducer;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCallUpdateWhenContractExistsItMustBeUpdated() throws Exception {

		JSONObject manuSisResultApi = new JSONObject("{\"data\": [ {\"id\": 123}, {\"id\": 321} ]}");
		when(manuSisApiClient.fetchAssets()).thenReturn(manuSisResultApi);

		when(assetsRepository.existsById(any())).thenReturn(true);

		// DO NOTHING
		doAnswer((invocation) -> {
			return null;
		}).when(assetsRepository).update(any());

		// DO NOTHING
		doAnswer((invocation) -> {
			return null;
		}).when(manuSisProducer).sendToPubSub(any());
		
		assetUpdateService.update();
		
		verify(manuSisApiClient, times(1)).fetchAssets();
		verify(assetsRepository, times(2)).update(any());
		verify(manuSisProducer, times(2)).sendToPubSub(any());
	}
	
	@Test
	void testCallUpdateWhenContractNotExistsItMustBeInserted() throws Exception {

		JSONObject manuSisResultApi = new JSONObject("{\"data\": [ {\"id\": 123}, {\"id\": 321} ]}");
		when(manuSisApiClient.fetchAssets()).thenReturn(manuSisResultApi);

		when(assetsRepository.existsById(any())).thenReturn(false);

		// DO NOTHING
		doAnswer((invocation) -> {
			return null;
		}).when(assetsRepository).insert(any());

		// DO NOTHING
		doAnswer((invocation) -> {
			return null;
		}).when(manuSisProducer).sendToPubSub(any());
		
		assetUpdateService.update();
		
		verify(manuSisApiClient, times(1)).fetchAssets();
		verify(assetsRepository, times(2)).insert(any());
		verify(manuSisProducer, times(2)).sendToPubSub(any());
	}
	
	@Test
	void testCallUpdateWhenOccursSomeErrorInOneContractItMustBeIgnored() throws Exception {

		JSONObject manuSisResultApi = new JSONObject("{\"data\": [ {\"id\": 123}, {\"id\": 321} ]}");
		when(manuSisApiClient.fetchAssets()).thenReturn(manuSisResultApi);

		when(assetsRepository.existsById("123")).thenThrow(RuntimeException.class);
		when(assetsRepository.existsById("321")).thenReturn(false);

		// DO NOTHING
		doAnswer((invocation) -> {
			return null;
		}).when(assetsRepository).insert(any());

		// DO NOTHING
		doAnswer((invocation) -> {
			return null;
		}).when(manuSisProducer).sendToPubSub(any());
		
		assetUpdateService.update();
		
		verify(manuSisApiClient, times(1)).fetchAssets();
		verify(assetsRepository, times(1)).insert(any());
		verify(manuSisProducer, times(1)).sendToPubSub(any());
	}
	
	@Test
	void testCallUpdateWhenOccursSomeErrorOnManuSisAPIItMustThrowUnexpectedException() throws Exception {

		when(manuSisApiClient.fetchAssets()).thenThrow(RuntimeException.class);
		
		assertThrows(UnexpectedException.class, ()->{
			assetUpdateService.update();
		});

		verify(manuSisApiClient, times(1)).fetchAssets();
	}

}
