package com.integrador.assets.builder;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.integrador.assets.constants.Pagination;
import com.integrador.assets.domain.Asset;
import com.integrador.assets.rest.response.AssetResponse;

public class AssetResponseBuilder {

	private AssetResponseBuilder() {
	}

	public static AssetResponse toAssetResponse(List<Asset> assets, Integer pageNumber, Integer pageSize,
			Integer totalResultCount) {
		if (pageNumber == null) {
			pageNumber = Pagination.DEFAULT_PAGE_NUMBER;
		}

		if (pageSize == null) {
			pageSize = Pagination.DEFAULT_PAGE_SIZE;
		}
		return AssetResponse.builder().pageNumber(pageNumber).pageSize(pageSize).total(totalResultCount)
				.result(assets.stream().map(JSONObject::toMap).collect(Collectors.toList())).build();
	}

}
