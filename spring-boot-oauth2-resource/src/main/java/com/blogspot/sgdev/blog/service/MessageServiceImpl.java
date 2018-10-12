package com.blogspot.sgdev.blog.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
public class MessageServiceImpl implements MessageService {
	private MessageSource messageSource;
	private final static Locale DEFAULT_LOCALE = Locale.US;
	
	public enum ResponseMessage {
		BAD_REQUEST("bad.request"),
		BAD_REQUEST_INCORRECT_TYPE_PROVIDED("bad.request.incorrect.type.provided"),
		BAD_REQUEST_REQUIRED_FIELD_MISSING("bad.request.required.field.missing"),
		BAD_REQUEST_NO_ID_PROVIDED("bad.request.no.id.provided"),
		BAD_REQUEST_ID_MUST_NOT_BE_PROVIDED("bad.request.id.must.not.be.provided"),
		BAD_REQUEST_NO_OBJECT_PROVIDED("bad.request.no.object.provided"),
		NOT_FOUND("not.found"),
		FORBIDDEN("access.is.denied"),
		INTERNAL_SERVER_ERROR("internal.server.error");
		
		private String message;
		
		ResponseMessage(String message) {
			this.message = message;
		}
		
		@Override
		public String toString() {
			return this.message;
		}
	}

	public MessageServiceImpl() {
		super();
	}

	@Autowired
	public MessageServiceImpl(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	public String format(ResponseMessage type) {
		return format(type, DEFAULT_LOCALE, new Object[] {});
	}
	
	@Override
	public String format(ResponseMessage type, Locale locale) {
		return format(type, locale, new Object[] {});
	}
	
	@Override
	public String format(ResponseMessage type, Object... input) {
		return format(type, DEFAULT_LOCALE, input);
	}
	
	@Override
	public String format(ResponseMessage type, Locale locale, Object... input) {
		return messageSource.getMessage(type.toString(), input , locale);
	}

	@Override
	public Map<String, Object> getMessage(ResponseMessage type) {
		return getMessage(type, DEFAULT_LOCALE, new Object[] {});
	}

	@Override
	public Map<String, Object> getMessage(ResponseMessage type, Locale locale) {
		return getMessage(type, locale, new Object[] {});
	}

	@Override
	public Map<String, Object> getMessage(ResponseMessage type, Object... input) {
		return getMessage(type, DEFAULT_LOCALE, input);
	}

	@Override
	public Map<String, Object> getMessage(ResponseMessage type,  Locale locale, Object... input) {
		Map<String, Object> result = new HashMap<>();
		result.put("timestamp", new Date());
		result.put("message",
				messageSource.getMessage(type.toString(), new Object[] { input }, locale));
		result.put("status", getCode(type.toString()));
		return result;
	}

	private static String getCode(String type) {
		//logic can be built in here to auto-generate status messages based on naming convention in enum.
		return type;
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
