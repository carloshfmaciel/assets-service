package com.integrador.assets.client.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("Writing assertions to ManuSisApiClient")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ManuSisApiClientTest {

	@InjectMocks
	private ManuSisApiClient manuSisApiClient;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);

		ReflectionTestUtils.setField(manuSisApiClient, "manuSisApiUrl", "https://www.someurl.com");
		ReflectionTestUtils.setField(manuSisApiClient, "manuSisApiToken", "tokenFake");
	}

	@Test
	void whenManusisApiAnswersWithHttpStatusCodeBetween200And300ItMustReturnsJsonResponseBody() throws Exception {
		HttpClient mockedHttpClient = mock(HttpClient.class);

		try (MockedStatic mockStatic = mockStatic(HttpClient.class)) {

			mockStatic.when(HttpClient::newHttpClient).thenReturn(mockedHttpClient);

			HttpResponse httpResponse = mock(HttpResponse.class);

			when(mockedHttpClient.send(any(), any())).thenReturn(httpResponse);
			when(httpResponse.body()).thenReturn("{\"is_readonly\": false, \"id\": 237469}");
			when(httpResponse.statusCode()).thenReturn(200);

			JSONObject response = manuSisApiClient.fetchAssets();

			// Inside scope
			assertEquals(false, response.getBoolean("is_readonly"));
			assertEquals(237469, response.getInt("id"));
		}
	}

	@Test
	void whenManusisApiAnswersWithHttpStatusCodeIsNotBetween200And300ItMustThrowsRuntimeException() throws Exception {
		HttpClient mockedHttpClient = mock(HttpClient.class);

		try (MockedStatic<HttpClient> mockStatic = mockStatic(HttpClient.class)) {

			mockStatic.when(HttpClient::newHttpClient).thenReturn(mockedHttpClient);

			HttpResponse httpResponse = mock(HttpResponse.class);

			when(mockedHttpClient.send(any(), any())).thenReturn(httpResponse);
			when(httpResponse.body()).thenReturn("{\"is_readonly\": false, \"id\": 237469}");
			when(httpResponse.statusCode()).thenReturn(404);

			assertThrows(RuntimeException.class, () -> manuSisApiClient.fetchAssets(),
					"it should throw RuntimeException");
		}
	}

	@Test
	void whenManusisApiAnswersWithHttpStatusCodeIsLessThan200ItMustThrowsRuntimeException() throws Exception {
		HttpClient mockedHttpClient = mock(HttpClient.class);

		try (MockedStatic<HttpClient> mockStatic = mockStatic(HttpClient.class)) {

			mockStatic.when(HttpClient::newHttpClient).thenReturn(mockedHttpClient);

			HttpResponse httpResponse = mock(HttpResponse.class);

			when(mockedHttpClient.send(any(), any())).thenReturn(httpResponse);
			when(httpResponse.body()).thenReturn("{\"is_readonly\": false, \"id\": 237469}");
			when(httpResponse.statusCode()).thenReturn(150);

			assertThrows(RuntimeException.class, () -> manuSisApiClient.fetchAssets(),
					"it should throw RuntimeException");
		}
	}

}
