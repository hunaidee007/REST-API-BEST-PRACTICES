package com.blogspot.sgdev.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractApplicationException extends RuntimeException {

	private static final long serialVersionUID = 8813342054849572445L;
	
	private String uuid;
	private String trace;
	private List<String> messages;

	public AbstractApplicationException() {
        super();
    }

	public AbstractApplicationException(String message, String uuid) {		
        super(message + " [ref="+uuid+"]");
        this.messages = new ArrayList<String>();
        this.messages.add(message);
        this.uuid = uuid;
        this.trace="this is a trace";
    }

	public AbstractApplicationException(List<String> messages, String uuid) {		
        super(messages.stream().map(String::toString).collect(Collectors.joining(", ")) + " [ref="+uuid+"]");
        this.messages = messages;
        this.uuid = uuid;
    }

	public AbstractApplicationException(String message, Throwable cause, String uuid) {
        super(message + " [ref="+uuid+"]", cause);
        this.messages = new ArrayList<String>();
        this.messages.add(message);
        this.uuid = uuid;
	}

	public AbstractApplicationException(List<String> messages, Throwable cause, String uuid) {		
        super(messages.stream().map(String::toString).collect(Collectors.joining(", ")) + " [ref="+uuid+"]", cause);
        this.messages = messages;
        this.uuid = uuid;
    }
	
	public AbstractApplicationException(Throwable cause, String uuid) {
        super(cause.getMessage() + " [ref="+uuid+"]", cause);
        this.messages = new ArrayList<String>();
        this.messages.add(cause.getMessage());
        this.uuid = uuid;		
	}	

	public String getUUID() {
		return uuid;
	}
	
	public List<String> getMessages() {
		return this.messages;
	}
	
	public abstract String getType();

}
