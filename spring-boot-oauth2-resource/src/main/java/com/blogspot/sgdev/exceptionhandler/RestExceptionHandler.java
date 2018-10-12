package com.blogspot.sgdev.exceptionhandler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blogspot.sgdev.blog.controllers.ErrorJson;
import com.blogspot.sgdev.exceptions.NotFoundException;
import com.blogspot.sgdev.exceptions.ValidationException;

//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice
public class RestExceptionHandler {/*extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleAllExceptions(HttpServletRequest req, Exception ex) {
		System.out.println("In handleAllExceptions");
		ErrorJson apiError = new ErrorJson(HttpStatus.NOT_FOUND,ex.getMessage());
		
		return buildResponseEntity(apiError);
		
	}
	
/*	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		System.out.println("In handleNotFoundException");
		ErrorJson apiError = new ErrorJson(HttpStatus.NOT_FOUND,ex.getMessage());
		
		return buildResponseEntity(apiError);
		
	}
	
	@ExceptionHandler(ValidationException.class)
	protected ResponseEntity<Object> handleValidationException(ValidationException ex) {
		System.out.println("In handleValidationException");
		ErrorJson apiError = new ErrorJson(HttpStatus.BAD_REQUEST,ex.getMessage(),ex.getErrors());
		System.out.println("-- in here RestExceptionHandler" + ex.getErrors().size());
		return buildResponseEntity(apiError);
	}*/
	
	  private ResponseEntity<Object> buildResponseEntity(ErrorJson apiError) {
	       return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.status));
	   }
}
