package com.pmp.pmpgateway.exception;

import java.util.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptionMethod(Exception ex, WebRequest req){
		log.info("In ExceptionHandler");
		
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getLocalizedMessage(), ex.getClass().getCanonicalName(),((ServerHttpRequest) req).getURI().getPath());
		return new ResponseEntity<>(exceptionMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
