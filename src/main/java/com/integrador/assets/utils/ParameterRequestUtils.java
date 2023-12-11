package com.integrador.assets.utils;

public class ParameterRequestUtils {

	public static String[] getFilterInValues(String value) {
		return value.split(":(?![^\\[]*\\])");
	}

}
