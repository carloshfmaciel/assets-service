package com.integrador.assets.client.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class APIClient {

    public JSONObject fetchAssets() throws Exception {
        String apiUrl = "https://millshomolog.manusis4.com/api/v1/assets";
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Token token=0c526f8c307a4cbd841da53de59698f1")
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