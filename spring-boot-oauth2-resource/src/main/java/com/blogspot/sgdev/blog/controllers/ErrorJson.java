package com.blogspot.sgdev.blog.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "timestamp","path","message", "errors" })
public class ErrorJson {
	
	@JsonProperty("status")
	public int status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	@JsonProperty("message")
	public String message;
	
	@JsonProperty("path")
	public String path;
	
	@JsonProperty("debug_message")
	public String debugMessage;
	
	@JsonProperty("sub_errors")
	private List<ApiSubError> subErrors;
	
	
	public ErrorJson(Map<String, Object> errorAttributesMap, List<ApiSubError> subErrors) {
		timestamp = LocalDateTime.now();
		
		this.status = (int) errorAttributesMap.get("status");
		this.path = (String) errorAttributesMap.get("path");
		this.message = (String) errorAttributesMap.get("message");
		this.debugMessage = (String) errorAttributesMap.get("debugMessage");
		this.subErrors =subErrors;
	}
}