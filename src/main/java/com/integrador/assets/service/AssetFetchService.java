package com.integrador.assets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.stereotype.Service;

import com.integrador.assets.domain.Asset;
import com.integrador.assets.mongo.repository.AssetRepository;
import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.rest.request.parser.AssetFetchRequestParser;

@Service
public class AssetFetchService {

	@Autowired
	private AssetRepository assetsRepository;

	public List<Asset> findByFilters(AssetFetchRequest request) {
		List<CriteriaDefinition> filters = AssetFetchRequestParser.getFilters(request);
		List<String> fieldsToReturn = AssetFetchRequestParser.getFields(request);
		Sort orderBy = AssetFetchRequestParser.getOrderBy(request);
		Pageable pagination = AssetFetchRequestParser.getPagination(request);

		return assetsRepository.findByFilters(filters, fieldsToReturn, orderBy, pagination);
	}
	
	public Asset findById(String id) {
		return assetsRepository.findById(id);
	}
}