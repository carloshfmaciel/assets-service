package com.integrador.assets.rest.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private int statusCode;

	private String exception;
	
	private String errorDescription;
	
}
