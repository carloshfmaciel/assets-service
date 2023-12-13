package com.integrador.assets.rest.request.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

import com.integrador.assets.rest.request.AssetFetchRequest;
import com.integrador.assets.utils.ParameterRequestUtils;

public class AssetFetchRequestParser {

	private static List<String> splitSemicolon(String fields) {
		return Optional.ofNullable(fields).map(value -> value.split(";"))
				.map(values -> Arrays.stream(values).map(String::trim).collect(Collectors.toList())).orElse(null);
	}

	public static boolean isASC(String sortDirection) {
		if (!StringUtils.hasText(sortDirection)) {
			throw new IllegalArgumentException();
		}
		return sortDirection.equalsIgnoreCase("asc");
	}

	public static Criteria getFilters(AssetFetchRequest request) {
		if (Objects.isNull(request) || Objects.isNull(request.getFilter())) {
			return null;
		}
		
		List<String> filterParameters = splitSemicolon(request.getFilter());
		List<Criteria> conditions = filterParameters.stream().map(filter -> {
			String[] fieldAndValue = ParameterRequestUtils.getFilterInValues(filter);

			String fieldName = fieldAndValue[0];

			String valueAsString = fieldAndValue[1];
			Boolean valueAsBoolean = getValueAsBoolean(fieldAndValue[1]);
			Long valueAsNumeric = getValueAsNumeric(fieldAndValue[1]);
			
			Collection<Criteria> criteriaList = new ArrayList<>();
		
			criteriaList.add(Criteria.where(fieldName).is(valueAsString));

			if (valueAsBoolean != null) {
				criteriaList.add(Criteria.where(fieldName).is(valueAsBoolean));
			}

			if (valueAsNumeric != null) {
				criteriaList.add(Criteria.where(fieldName).is(valueAsNumeric));
			}
			
			return new Criteria().orOperator(criteriaList);
			
		}).collect(Collectors.toList());
		
		return new Criteria().andOperator(conditions);
	}

	private static Boolean getValueAsBoolean(String value) {
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			return Boolean.valueOf(value);
		}
		return null;
	}

	private static Long getValueAsNumeric(String value) {
		try {
			Long valueAsLong = Long.valueOf(value);
			return valueAsLong;
		} catch (Exception e) {
			return null;
		}
	}

	public static List<String> getFields(AssetFetchRequest request) {
		return splitSemicolon(request.getFields());
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
