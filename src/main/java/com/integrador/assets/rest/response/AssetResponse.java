package com.integrador.assets.rest.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetResponse {

	private int pageSize;

	private int pageNumber;
	
	private int total;

	private List<Map<String, Object>> result;

}
