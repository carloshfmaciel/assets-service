package com.integrador.assets.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrador.assets.client.api.ManuSisApiClient;
import com.integrador.assets.mongo.repository.AssetRepository;

@Service
public class AssetFetchService {

	@Autowired
	private ManuSisApiClient manuSisApiClient;

	@Autowired
	private AssetRepository assetsRepository;

	public void createAssets() {
		try {
			JSONObject assetsResponse = manuSisApiClient.fetchAssets();
			JSONArray jsonArray = assetsResponse.getJSONArray("data");
			assetsRepository.save(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}