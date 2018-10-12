package com.blogspot.sgdev.exceptions;

import java.util.List;
import java.util.UUID;

public class SystemException extends AbstractApplicationException {

	private static final long serialVersionUID = 6653845545088146840L;
	private final String TYPE = "https://www.santam.co.za/api/errors/system-error";
	
	public SystemException(String message) {
		super(message, UUID.randomUUID().toString());
	}
	
	public SystemException(String message, String uuid) {
		super(message, uuid);
	}

	public SystemException(List<String> messages) {
		super(messages, UUID.randomUUID().toString());
	}

	public SystemException(List<String> messages, String uuid) {
		super(messages, uuid);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause, UUID.randomUUID().toString());
	}
	
	public SystemException(String message, Throwable cause, String uuid) {
		super(message, cause, uuid);
	}

	public SystemException(List<String> messages, Throwable cause) {
		super(messages, cause, UUID.randomUUID().toString());
	}

	public SystemException(List<String> messages, Throwable cause, String uuid) {
		super(messages, cause, uuid);
	}
	
	
	@Override
	public String getType() {
		return this.TYPE;
	}
	
}
