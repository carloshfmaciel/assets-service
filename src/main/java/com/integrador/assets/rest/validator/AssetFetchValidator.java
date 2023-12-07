package com.integrador.assets.rest.validator;

import java.util.regex.Pattern;
import org.springframework.util.StringUtils;
import com.integrador.assets.exception.InvalidParameterException;
import com.integrador.assets.rest.request.AssetFetchRequest;

public class AssetFetchValidator {

	private static final String REGEX_FILTER = "([\\w.]+:(\\[\\w+(,\\s*\\w+)*\\]+))|([\\w.|\\W.]+:[\\w.|\\W.]+){0,}(;{1,1}[\\w.]+:[\\w.]+){0,}";
	private static final String REGEX_ORDERBY = "([\\w.]+:(asc|desc))(;{1,1}[\\w.]+:(asc|desc)){0,}";

	public static void validate(AssetFetchRequest request) {
		if (StringUtils.hasText(request.getFilter())) {
			if (!Pattern.matches(REGEX_FILTER, request.getFilter())) {
				throw new InvalidParameterException("Formato do parâmetro filter inválido!");
			}
		}

		if (StringUtils.hasText(request.getOrderBy())) {
			if (!Pattern.matches(REGEX_ORDERBY, request.getOrderBy().toLowerCase())) {
				throw new InvalidParameterException("Formato do parâmetro orderBy inválido!");
			}
		}
	}
}
