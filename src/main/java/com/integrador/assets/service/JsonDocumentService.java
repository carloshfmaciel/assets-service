package com.integrador.assets.service;

import com.integrador.assets.domain.JsonDocument;

// Pacote application
public interface JsonDocumentService {
    void saveJsonDocument(JsonDocument jsonDocument);
    void closeConnection();
}
