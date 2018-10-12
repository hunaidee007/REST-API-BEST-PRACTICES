package com.blogspot.sgdev.exceptions;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundException extends AbstractApplicationException {

	private static final long serialVersionUID = 6653845545088146830L;
	
	private final String TYPE = "https://www.santam.co.za/api/errors/not-found-error";

	public NotFoundException(String message) {
		super(message, UUID.randomUUID().toString());
	}
	
	public NotFoundException(String message, String uuid) {
		super(message, uuid);
	}

	public NotFoundException(List<String> messages) {
		super(messages, UUID.randomUUID().toString());
	}	

	public NotFoundException(List<String> messages, String uuid) {
		super(messages, uuid);
	}
		
	@Override
	public String getType() {
		return this.TYPE;
	}
	
}
