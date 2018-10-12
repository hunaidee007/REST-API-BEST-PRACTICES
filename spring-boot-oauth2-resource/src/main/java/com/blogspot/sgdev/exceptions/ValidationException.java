package com.blogspot.sgdev.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.blogspot.sgdev.blog.controllers.ApiSubError;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ValidationException extends AbstractApplicationException {

	private static final long serialVersionUID = 6653845545088146830L;
	
	private List<ApiSubError> errors = new ArrayList<ApiSubError>();
	
	private final String TYPE = "https://www.santam.co.za/api/errors/validation-error";

	public ValidationException(String message) {
		super(message, UUID.randomUUID().toString());
	}
	
	public ValidationException( List<ApiSubError> errors,String message, String uuid) {
		super(message, uuid);
		this.errors=errors;
		System.out.println(" -- in here Validation exceptrion : " + errors.size());
		
	}

	public ValidationException(List<String> messages) {
		super(messages, UUID.randomUUID().toString());
	}	

	public ValidationException(List<String> messages, String uuid) {
		super(messages, uuid);
	}


	@Override
	public String getType() {
		return this.TYPE;
	}
	
	public List<ApiSubError> getErrors() {
		return errors;
	}
	
	public void addError(ApiSubError e) {
		errors.add(e);
	}
	
}
