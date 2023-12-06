package com.integrador.assets.rest.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.integrador.assets.domain.Asset;
import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.service.AssetFetchService;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/assets")
@SuppressWarnings("rawtypes")
public class AssetFetchController {

	private AssetFetchService assetFetchService;

	@GetMapping("/getByFilters")
	public ResponseEntity getByFields(@RequestBody AssetFetchRequest request) {
		List<Asset> result = assetFetchService.findByFilters(request);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity getById(@PathParam("id") String id) {
		Asset result = assetFetchService.findById(id);
		return ResponseEntity.ok(result);
	}
}
