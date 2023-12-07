package com.integrador.assets.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integrador.assets.service.AssetUpdateService;

@RestController
@RequestMapping("/api/v1/assets")
@SuppressWarnings("rawtypes")
public class AssetUpdateController {

	@Autowired
	private AssetUpdateService assetUpdateService;

	@PostMapping("/manuSis/import")
	public ResponseEntity importFromManuSis() {
		assetUpdateService.update();
		return ResponseEntity.ok().build();
	}

}
