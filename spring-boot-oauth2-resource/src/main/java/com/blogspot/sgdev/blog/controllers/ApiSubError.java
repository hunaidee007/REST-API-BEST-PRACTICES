package com.blogspot.sgdev.blog.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "field", "rejectedValue","message" })

public class ApiSubError {
	
	@JsonProperty("field")
	private String field;
	
	@JsonProperty("rejectedValue")
	private Object rejectedValue;
	
	@JsonProperty("message")
	private String message;

	public ApiSubError(String field, String message) {
		
		
		this.field = field;
		this.message = message;
		
		System.out.println("Creating sub error message");
	}

	public ApiSubError(String field, Object rejectedValue, String message) {
		super();
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.message = message;
	}
	
	
}

