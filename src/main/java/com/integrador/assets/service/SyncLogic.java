package com.integrador.assets.service;

import com.integrador.assets.client.api.APIClient;
import com.integrador.assets.domain.JsonDocument;
import com.integrador.assets.repository.MongoDbRepository;
import com.integrador.assets.service.AssetService;
import com.integrador.assets.service.JsonDocumentService;

import org.json.JSONArray;
import org.json.JSONObject;

public class SyncLogic {

    public static void sync() {
        try {
            APIClient apiClient = new APIClient();
            AssetService assetService = new AssetService(apiClient);
            JSONObject jsonResponse = apiClient.fetchAssets();

            // Obtém o array "data" do JSON
            JSONArray dataArray = jsonResponse.getJSONArray("data");

            // Itera sobre os objetos no array "data"
            for (int i = 0; i < dataArray.length(); i++) {
                // Obtém o objeto JSON atual
                JSONObject dataObject = dataArray.getJSONObject(i);

                JsonDocumentService jsonDocumentService = new MongoDbRepository();
                JsonDocument jsonDocument = new JsonDocument(dataObject);

                // Validador: Verifica se a data de atualização é diferente
                if (shouldSync(jsonDocumentService, jsonDocument)) {
                    jsonDocumentService.saveJsonDocument(jsonDocument);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean shouldSync(JsonDocumentService jsonDocumentService, JsonDocument jsonDocument) {
        System.out.println(jsonDocumentService);

        // Exemplo: return jsonDocumentService.isUpdated(jsonDocument);
        return true; // Para simplificar, sempre sincroniza neste exemplo
    }
}
