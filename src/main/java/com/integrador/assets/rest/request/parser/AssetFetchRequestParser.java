package com.integrador.assets.rest.request.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.util.StringUtils;

import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.utils.ParameterRequestUtils;

public class AssetFetchRequestParser {

	private static List<String> splitSemicolon(String fields) {
		return Optional.ofNullable(fields).map(value -> value.split(";"))
				.map(values -> Arrays.stream(values).map(String::trim).collect(Collectors.toList())).orElse(null);
	}

	private static boolean isASC(String sortDirection) {
		if (!StringUtils.hasText(sortDirection)) {
			throw new IllegalArgumentException();
		}
		return sortDirection.equalsIgnoreCase("asc");
	}

	public static List<CriteriaDefinition> getFilters(AssetFetchRequest request) {
		if(request.getFilter() == null) {
			return null;
		}
		
		List<String> filterParameters = splitSemicolon(request.getFilter());
		return filterParameters.stream().map(filter -> {
			String[] fieldAndValue = ParameterRequestUtils.getFilterInValues(filter);

			String fieldName = fieldAndValue[0];
			String value = fieldAndValue[1];

			return Criteria.where(fieldName).is(value);
		}).collect(Collectors.toList());
	}
	
	public static List<String> getFields(AssetFetchRequest request) {
		return splitSemicolon(request.getFilter());
	}

	public static Sort getOrderBy(AssetFetchRequest request) {
		List<String> orderBySplit = splitSemicolon(request.getOrderBy());

		List<Order> orderByList = orderBySplit.stream().map(orderBy -> {
			String[] fieldAndSortDirection = orderBy.split(":");
			String field = fieldAndSortDirection[0];
			String sortDirection = fieldAndSortDirection[1];

			return isASC(sortDirection) ? Order.asc(field) : Order.desc(field);
		}).collect(Collectors.toList());

		return Sort.by(orderByList);
	}

	public static Pageable getPagination(AssetFetchRequest request) {
		return PageRequest.of(request.getPageNumber(), request.getPageSize());
	}

}
