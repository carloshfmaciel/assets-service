package com.integrador.assets.rest.request;

import org.springframework.util.StringUtils;

import com.integrador.assets.constants.Pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
		}else {
			pageNumber = Pagination.DEFAULT_PAGE_NUMBER-1;
		}
		return pageNumber;
	}

	public Integer getPageSize() {
		if (pageSize == null) {
			pageSize = 30;
		}
		return pageSize;
	}

	public String getOrderBy() {
		if (!StringUtils.hasText(orderBy)) {
			orderBy = Pagination.DEFAULT_ORDER_BY;
		}
		return orderBy;
	}

}
