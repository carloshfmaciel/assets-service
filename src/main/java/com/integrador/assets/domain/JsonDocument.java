package com.integrador.assets.domain;

import org.json.JSONObject;

// Pacote domain
public class JsonDocument {
    private final String jsonString;

    public JsonDocument(JSONObject jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            throw new IllegalArgumentException("JSON string não pode ser nula ou vazia.");
        }
        this.jsonString = String.valueOf(jsonString);
    }

    public String getJsonString() {
        return jsonString;
    }
}
