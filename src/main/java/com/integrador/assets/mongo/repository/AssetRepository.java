package com.integrador.assets.mongo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.integrador.assets.domain.Asset;
import com.integrador.assets.exception.NotFoundException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

@Component
public class AssetRepository {

	public static final String COLLECTION_NAME = "asset";
	public static final String DATABASE_NAME = "ms-manusis";

	@Autowired
	private MongoTemplate mongoTemplate;

	public void save(JSONArray jsonArray) {
		jsonArray.forEach(itemJsonObject -> {
			Document document = Document.parse(itemJsonObject.toString());
			document.append("lastUpdateDate", LocalDateTime.now());

			if (document.get("id") != null) {

				Bson filterById = Filters.eq("id", document.get("id"));

				boolean exists = mongoTemplate.getCollection(COLLECTION_NAME).find(filterById).first() != null;

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

	public Asset findById(String id) {
		FindIterable<Document> documentIterable = mongoTemplate.getCollection(COLLECTION_NAME)
				.find(Filters.eq("id", id));
		if (!documentIterable.cursor().hasNext()) {
			throw new NotFoundException();
		}
		return new Asset(documentIterable.first().toString());
	}

	public List<Asset> findByFilters(List<CriteriaDefinition> filters, List<String> fieldsToReturn, Sort sort,
			Pageable pagination) {
		Query query = new Query();
		if (filters != null) {
			filters.forEach(query::addCriteria);
		}

		if (fieldsToReturn != null) {
			fieldsToReturn.forEach(query.fields()::include);
		}

		if (sort != null) {
			query.with(sort);
		}

		query.with(pagination);

		List<String> result = mongoTemplate.find(query, String.class, COLLECTION_NAME);
		return result.stream().map(obj -> new Asset(obj)).collect(Collectors.toList());
	}

}
