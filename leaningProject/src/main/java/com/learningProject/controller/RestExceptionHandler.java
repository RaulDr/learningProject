package com.learningProject.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.learningProject.view.ErrorView;

import javassist.NotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { NotFoundException.class})
	public ResponseEntity<Object> handleNotFound( NotFoundException ex, WebRequest request){
		return handleExceptionInternal(ex, new ErrorView(ex.getMessage(), ""), 
		          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
