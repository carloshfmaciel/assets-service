package com.integrador.assets.rest.validator;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.integrador.assets.exception.InvalidParameterException;
import com.integrador.assets.rest.request.AssetFetchRequest;

public class AssetFetchValidator {

	private AssetFetchValidator() {
	}

	private static final String REGEX_FILTER = "([\\w.]+:(\\[\\w+(,\\s*\\w+)*\\]+))|([\\w.|\\W.]+:[\\w.|\\W.]+){0,}(;{1,1}[\\w.]+:[\\w.]+){0,}";
	private static final String REGEX_ORDERBY = "([\\w.]+:(asc|desc))(;{1,1}[\\w.]+:(asc|desc)){0,}";

	public static void validate(AssetFetchRequest request) {
		if (StringUtils.hasText(request.getFilter()) && !Pattern.matches(REGEX_FILTER, request.getFilter())) {
			throw new InvalidParameterException("Formato do parâmetro filter inválido!");
		}

		if (request.getPageNumber() < 0) {
			throw new InvalidParameterException("Parâmetro pageNumber deve ser maior que 0!");
		}

		if (request.getPageSize() < 1) {
			throw new InvalidParameterException("Parâmetro pageSize deve ser maior que 0!");
		}

		if (request.getPageSize() > 50) {
			throw new InvalidParameterException("Parâmetro pageSize deve ser menor ou igual 50!");
		}

		if (!Pattern.matches(REGEX_ORDERBY, request.getOrderBy().toLowerCase())) {
			throw new InvalidParameterException("Formato do parâmetro orderBy inválido!");
		}
	}
}
