package com.integrador.assets.service;

import java.time.LocalDateTime;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrador.assets.builder.ManuSisMessageBuilder;
import com.integrador.assets.client.api.ManuSisApiClient;
import com.integrador.assets.constants.ManuSisMessageAction;
import com.integrador.assets.exception.UnexpectedException;
import com.integrador.assets.mongo.repository.AssetRepository;
import com.integrador.assets.pubsub.producer.ManuSisProducer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AssetUpdateService {

	@Autowired
	private ManuSisApiClient manuSisApiClient;

	@Autowired
	private AssetRepository assetsRepository;

	@Autowired
	private ManuSisProducer manuSisProducer;

	public void update() {
		try {
			JSONObject assetsResponse = manuSisApiClient.fetchAssets();
			JSONArray jsonArray = assetsResponse.getJSONArray("data");

			jsonArray.forEach(item -> {
				try {
					Document document = Document.parse(item.toString());
					document.append("lastUpdate", LocalDateTime.now());
					String id = document.get("id").toString();
					document.append("_id", id);
					boolean exists = assetsRepository.existsById(id);
					if (exists) {
						assetsRepository.update(document);
						manuSisProducer.sendToPubSub(ManuSisMessageBuilder.toMessage(id, ManuSisMessageAction.UPDATE));
					} else {
						assetsRepository.insert(document);
						manuSisProducer.sendToPubSub(ManuSisMessageBuilder.toMessage(id, ManuSisMessageAction.INSERT));
					}
				} catch (Exception e) {
					log.error(String.format("Error to process message item %s", item.toString()), e);
				}
			});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new UnexpectedException(e);
		}
	}
}