package com.integrador.assets.mongo.repository;

import org.bson.Document;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.bulk.BulkWriteResult;

@Component
public class AssetRepository {

	private static final String COLLECTION_NAME = "asset";

	@Autowired
	private MongoTemplate mongoTemplate;

	public void save(JSONArray jsonArray) {
		BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.ORDERED, COLLECTION_NAME);

		jsonArray.forEach(itemJsonObject -> {
			Document document = Document.parse(itemJsonObject.toString());
			bulkOps.insert(document);
		});

		bulkOps.execute();
	}

	@Transactional
	public void deleteAllAndSaveAll(JSONArray jsonArray) {

		mongoTemplate.getCollection(COLLECTION_NAME).deleteMany(new Document());

		BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.ORDERED, COLLECTION_NAME);
		jsonArray.forEach(itemJsonObject -> {
			Document document = Document.parse(itemJsonObject.toString());
			bulkOps.insert(document);
		});
		BulkWriteResult result = bulkOps.execute();

		System.out.println(result.getInsertedCount());
	}

	public void deleteAll() {
		mongoTemplate.getCollection(COLLECTION_NAME).deleteMany(new Document());
	}

}
