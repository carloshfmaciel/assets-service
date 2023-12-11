package com.integrador.assets.rest.controller;

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

@RestController
@RequestMapping("/api/v1/assets")
@SuppressWarnings("rawtypes")
public class AssetFetchController implements AssetFetchControllerSwagger {

	@Autowired
	private AssetFetchService assetFetchService;

	@GetMapping("/getByFilters")
	public ResponseEntity getByFilters(@RequestParam(name = "filters", required = false) String filters,
			@RequestParam(name = "fields", required = false) String fields,
			@RequestParam(name = "orderBy", required = false) String orderBy,
			@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) {
		AssetFetchRequest request = AssetFetchRequest.builder().filter(filters).fields(fields).orderBy(orderBy)
				.pageNumber(pageNumber).pageSize(pageSize).build();
		AssetFetchValidator.validate(request);
		return ResponseEntity.ok(assetFetchService.findByFilters(request));
	}

	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") String id) {
		Asset result = assetFetchService.findById(id);
		return ResponseEntity.ok(result.toMap());
	}
}
