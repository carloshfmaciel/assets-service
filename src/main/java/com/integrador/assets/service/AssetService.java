// Camada de Aplicação
package com.integrador.assets.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.integrador.assets.client.api.APIClient;

public class AssetService {

    private final APIClient apiClient;

    public AssetService(APIClient apiClient) {
        this.apiClient = apiClient;
    }

    public void fetchAndPrintAssets() {
        try {
            JSONObject jsonResponse = apiClient.fetchAssets();

            // Obtém o array "data" do JSON
            JSONArray dataArray = jsonResponse.getJSONArray("data");

            // Itera sobre os objetos no array "data"
            for (int i = 0; i < dataArray.length(); i++) {
                // Obtém o objeto JSON atual
                JSONObject dataObject = dataArray.getJSONObject(i);

                // Obtém e imprime o valor do campo "code"
                System.out.println(dataObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}