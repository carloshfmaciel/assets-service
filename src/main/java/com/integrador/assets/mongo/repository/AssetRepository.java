package com.integrador.assets.mongo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

@Component
public class AssetRepository {

	private static final String COLLECTION_NAME = "asset";

	@Autowired
	private MongoTemplate mongoTemplate;

	public void save(JSONArray jsonArray) {

		jsonArray.forEach(itemJsonObject -> {
			Document document = Document.parse(itemJsonObject.toString());
			document.append("lastUpdateDate", LocalDateTime.now());

			if (document.get("id") != null) {

				Bson filterById = Filters.eq("id", document.get("id"));

				boolean exists = mongoTemplate.getCollection(COLLECTION_NAME).find(filterById).first().isEmpty();

				if (exists) {
					mongoTemplate.getCollection(COLLECTION_NAME).replaceOne(filterById, document);
				} else {
					mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
				}
			}

		});
	}

	public void deleteAll() {
		mongoTemplate.getCollection(COLLECTION_NAME).deleteMany(new Document());
	}

	public Document findById(String id) {
		FindIterable<Document> documentIterable = mongoTemplate.getCollection(COLLECTION_NAME)
				.find(Filters.eq("id", id));
		return documentIterable.first();
	}

	public Set<JSONObject> findByFilters(List<String> filters, Sort sort, Pageable pageable) {
		Query query = new Query();
		query.addCriteria(Criteria.where("").is(""));
		query.with(pageable).with(sort);
		List<String> result = mongoTemplate.find(query, String.class, COLLECTION_NAME);
		return result.stream().map(obj -> new JSONObject(obj)).collect(Collectors.toSet());
	}

}
