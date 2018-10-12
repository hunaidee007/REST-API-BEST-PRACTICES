package com.blogspot.sgdev.blog.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.blogspot.sgdev.blog.controllers.ApiSubError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.blogspot.sgdev.exceptions.ValidationException;

@RestController
@RequestMapping("/error")
public class CatchAllErrorController implements ErrorController {

	private final ErrorAttributes errorAttributes;

	@Autowired
	public CatchAllErrorController(ErrorAttributes errorAttributes) {
		Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
		this.errorAttributes = errorAttributes;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping
	public ResponseEntity<Object> error(HttpServletRequest aRequest, WebRequest webRequest,
			HttpServletResponse response) {

		Map<String, Object> errorAttributesMap = getErrorAttributes(aRequest, webRequest, getTraceParameter(aRequest));
		System.out.println("Errors - "+errorAttributesMap.get("errors"));
		List<ApiSubError> apiSubErrors = new ArrayList<ApiSubError>();
		if(errorAttributesMap.get("errors")!=null) {
			List<Object> objecterrors= (List)errorAttributesMap.get("errors");
			System.out.println("-- looping errors");
			System.out.println(objecterrors);
			for(Object objectError : objecterrors) {
				FieldError a= (FieldError)objectError;
				ApiSubError apiSubError = new ApiSubError(a.getField(),a.getRejectedValue(),a.getDefaultMessage());
				apiSubErrors.add(apiSubError);
			}
			
		}
		Throwable error = errorAttributes.getError(webRequest);
		if (error instanceof ValidationException) {
			ValidationException ex = (ValidationException) error;
			ErrorJson apiError = new ErrorJson(errorAttributesMap, ex.getErrors());
			return new ResponseEntity<>(apiError, HttpStatus.valueOf(response.getStatus()));
		}

		return new ResponseEntity<>(createErrorResponse(errorAttributesMap,apiSubErrors), HttpStatus.valueOf(response.getStatus()));
	}

	private ErrorJson createErrorResponse(Map<String, Object> errorAttributesMap, List<ApiSubError> errors) {
		ErrorJson e = new ErrorJson(errorAttributesMap,errors);
		return e;
	}

	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, WebRequest webRequest,
			boolean includeStackTrace) {
		return errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
	}

	private ResponseEntity<Object> buildResponseEntity(ErrorJson apiError) {
		return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.status));
	}

}