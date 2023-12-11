package com.integrador.assets.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Asset extends JSONObject {

	public Asset(String source) throws JSONException {
		super(source);
	}

}
