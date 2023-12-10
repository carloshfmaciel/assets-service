package com.integrador.assets.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.integrador.assets.domain.Asset;
import com.integrador.assets.rest.controller.swagger.AssetFetchControllerSwagger;
import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.rest.validator.AssetFetchValidator;
import com.integrador.assets.service.AssetFetchService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/assets")
@SuppressWarnings("rawtypes")
public class AssetFetchController implements AssetFetchControllerSwagger {

	@Autowired
	private AssetFetchService assetFetchService;

	@GetMapping("/getByFilters")
	public ResponseEntity getByFields(@RequestParam("filters") String filters, @RequestParam("fields") String fields,
			@RequestParam("orderBy") String orderBy, @RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("pageSize") Integer pageSize) {
		AssetFetchRequest request = AssetFetchRequest.builder()
										.filter(filters)
										.fields(fields)
										.orderBy(orderBy)
										.pageNumber(pageNumber)
										.pageSize(pageSize)
										.build();
		AssetFetchValidator.validate(request);
		List<Asset> result = assetFetchService.findByFilters(request);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") String id) {
		Asset result = assetFetchService.findById(id);
		return ResponseEntity.ok(result);
	}
}
