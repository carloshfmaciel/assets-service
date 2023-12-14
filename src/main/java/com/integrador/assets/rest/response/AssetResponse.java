package com.integrador.assets.rest.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetResponse {

	private int pageSize;

	private int pageNumber;
	
	private int total;

	private List<Map<String, Object>> result;

}
