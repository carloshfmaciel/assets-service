package com.integrador.assets.rest.request;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class AssetFetchRequest {

	private String filter;

	private String fields;

	private String orderBy;

	private Integer pageNumber;

	private Integer pageSize;

	public Integer getPageNumber() {
		if (pageNumber != null) {
			if (pageNumber.equals(0)) {
				return pageNumber;
			} else {
				return pageNumber - 1;
			}
		}
		return pageNumber;
	}

	public Integer getPageSize() {
		if (pageSize == null) {
			pageSize = 50;
		}
		return pageNumber;
	}

	public String getOrderBy() {
		if (!StringUtils.hasText(orderBy)) {
			orderBy = "created_at:desc";
		}
		return orderBy;
	}

}
