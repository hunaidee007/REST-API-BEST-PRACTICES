package com.blogspot.sgdev.blog.service;

import com.blogspot.sgdev.blog.service.MessageServiceImpl.ResponseMessage;
import java.util.Locale;
import java.util.Map;

public interface MessageService {

	public String format(ResponseMessage type);

	public String format(ResponseMessage type, Locale locale);

	public String format(ResponseMessage type, Object... input);

	public String format(ResponseMessage type, Locale locale, Object... input);

	public Map<String, Object> getMessage(ResponseMessage type);

	public Map<String, Object> getMessage(ResponseMessage type, Locale locale);

	public Map<String, Object> getMessage(ResponseMessage type, Object... input);

	public Map<String, Object> getMessage(ResponseMessage type,  Locale locale, Object... input);

}
