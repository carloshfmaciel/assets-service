package com.integrador.assets.rest.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.websocket.server.PathParam;

@OpenAPIDefinition(info = @Info(title = "Mills Assets Service - OpenAPI 3.0", version = "v1.0", 
	description = ""))
@SuppressWarnings("rawtypes")
public interface AssetFetchControllerSwagger {
	
	@Operation(summary = "Find assets by filters", description = "REST Endpoint that finds assets by filters")
	@Tags(value = @Tag(name = "assets", description = "Everything about assets"))
	@GetMapping(value = "/getByFilters", produces = "application/json", consumes = "application/json")
	public ResponseEntity getByFilters(@RequestParam("filters") String filters, @RequestParam("fields") String fields,
			@RequestParam("orderBy") String orderBy, @RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("pageSize") Integer pageSize);
	
	@Operation(summary = "Find asset by id", description = "REST Endpoint that finds asset by id")
	@Tags(value = @Tag(name = "assets", description = "Everything about assets"))
	@GetMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity getById(@PathParam("id") String id);

}
