package com.integrador.assets.cron;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.integrador.assets.service.AssetUpdateService;

@DisplayName("Writing assertions to AssetCron")
public class AssetCronTest {

	@InjectMocks
	private AssetCron assetCron;

	@Mock
	private AssetUpdateService assetUpdateService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCallUpdateMethodOnAssetUpdateService() {
		assetCron.execute();

		verify(assetUpdateService, times(1)).update();
	}

}
