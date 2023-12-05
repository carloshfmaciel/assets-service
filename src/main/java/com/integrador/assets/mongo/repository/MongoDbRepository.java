package com.integrador.assets.mongo.repository;

import com.integrador.assets.domain.JsonDocument;
import com.integrador.assets.service.JsonDocumentService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDbRepository implements JsonDocumentService {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "ms-manusis";
    private static final String COLLECTION_NAME = "asset";

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDbRepository() {
        this.mongoClient = MongoClients.create(CONNECTION_STRING);
        this.database = mongoClient.getDatabase(DATABASE_NAME);
    }

    public MongoCollection<Document> getCollection() {
        return database.getCollection(COLLECTION_NAME);
    }


    @Override
    public void saveJsonDocument(JsonDocument jsonDocument) {
        // Parse do JSON para Document
        Document document = Document.parse(jsonDocument.getJsonString());

        // Extrair o ID do Document
        Object documentIdObject = document.get("id");

        if (documentIdObject != null) {
            // Verificar se o Document com o ID já existe na coleção
            Bson filter = Filters.eq("id", documentIdObject);

            Document existingDocument = getCollection().find(filter).first();

            if (existingDocument != null) {
                //System.out.println("já existe");
                getCollection().replaceOne(filter, document);
                return;
            }
        }

        // Se o Document com o ID não existe ou o ID é nulo, inserir na coleção
        getCollection().insertOne(document);
    }



    @Override
    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
