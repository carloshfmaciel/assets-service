package com.integrador.assets.utils;

import java.util.Arrays;

public class ParameterRequestUtils {
	
	public static String[] getFilterInValues(String value) {

		String[] values = value.replace("[", "").replace("]", "").replace("\"", "").replace("'", "").split(",");

		// REMOVE SPACE AT THE BEGGINING AND THE END
		return Arrays.stream(values).map(String::trim).toArray(String[]::new);
	}

}
