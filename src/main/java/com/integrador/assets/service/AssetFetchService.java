package com.integrador.assets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.stereotype.Service;

import com.integrador.assets.builder.AssetResponseBuilder;
import com.integrador.assets.domain.Asset;
import com.integrador.assets.exception.NotFoundException;
import com.integrador.assets.mongo.repository.AssetRepository;
import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.rest.request.parser.AssetFetchRequestParser;
import com.integrador.assets.rest.response.AssetResponse;

@Service
public class AssetFetchService {

	@Autowired
	private AssetRepository assetsRepository;

	public AssetResponse findByFilters(AssetFetchRequest request) {
		Criteria filters = AssetFetchRequestParser.getFilters(request);
		List<String> fieldsToReturn = AssetFetchRequestParser.getFields(request);
		Sort orderBy = AssetFetchRequestParser.getOrderBy(request);
		Pageable pagination = AssetFetchRequestParser.getPagination(request);

		List<Asset> result = assetsRepository.findByFilters(filters, fieldsToReturn, orderBy, pagination);

		if (result == null || result.isEmpty()) {
			throw new NotFoundException();
		}

		int totalResultCount = assetsRepository.countByFilters(filters);

		return AssetResponseBuilder.toAssetResponse(result, request.getPageNumber() + 1, request.getPageSize(),
				totalResultCount);
	}

	public Asset findById(String id) {
		return assetsRepository.findById(id);
	}
}