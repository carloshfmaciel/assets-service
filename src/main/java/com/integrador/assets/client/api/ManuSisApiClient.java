package com.integrador.assets.client.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ManuSisApiClient {

	@Value("${api.external.manusis.url}")
	private String manuSisApiUrl;

	@Value("${api.external.manusis.token}")
	private String manuSisApiToken;

	public JSONObject fetchAssets() throws Exception {
		HttpClient httpClient = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(manuSisApiUrl))
				.header("Content-Type", "application/json")
				.header("Authorization", "Token token=" + manuSisApiToken)
				.header("app-origin", "API")
				.header("x-language", "pt_BR")
				.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() >= 200 && response.statusCode() < 300) {
			return new JSONObject(response.body());
		} else {
			throw new RuntimeException("Erro na requisição. Código de resposta: " + response.statusCode());
		}
	}
}