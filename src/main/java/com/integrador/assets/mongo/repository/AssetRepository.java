package com.integrador.assets.mongo.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.integrador.assets.domain.Asset;
import com.integrador.assets.exception.NotFoundException;

@Component
public class AssetRepository {

	public static final String COLLECTION_NAME = "asset";
	public static final String DATABASE_NAME = "ms-manusis";

	@Autowired
	private MongoTemplate mongoTemplate;

	public void insert(Document document) {
		mongoTemplate.getCollection(COLLECTION_NAME).insertOne(document);
	}

	public void update(Document document) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(Integer.valueOf(document.get("id").toString())));
		mongoTemplate.replace(query, document, COLLECTION_NAME);
	}

	public void deleteAll() {
		mongoTemplate.getCollection(COLLECTION_NAME).deleteMany(new Document());
	}

	public Asset findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(Integer.valueOf(id)));
		String asset = mongoTemplate.findOne(query, String.class, COLLECTION_NAME);
		if (asset == null) {
			throw new NotFoundException();
		}
		return new Asset(asset);
	}

	public boolean existsById(String id) {
		try {
			this.findById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Asset> findByFilters(Criteria filters, List<String> fieldsToReturn, Sort sort, Pageable pagination) {
		Query query = new Query();
		if (filters != null) {
			query.addCriteria(filters);
		}

		if (fieldsToReturn != null) {
			fieldsToReturn.forEach(query.fields()::include);
		}

		if (sort != null) {
			query.with(sort);
		}

		query.with(pagination);

		List<String> result = mongoTemplate.find(query, String.class, COLLECTION_NAME);
		
		if(CollectionUtils.isEmpty(result)) {
			throw new NotFoundException();
		}
		
		return result.stream().map(obj -> new Asset(obj)).collect(Collectors.toList());
	}

	public int countByFilters(Criteria filters) {
		Query query = new Query();
		if (filters != null) {
			query.addCriteria(filters);
		}

		return (int) mongoTemplate.count(query, String.class, COLLECTION_NAME);
	}

}
