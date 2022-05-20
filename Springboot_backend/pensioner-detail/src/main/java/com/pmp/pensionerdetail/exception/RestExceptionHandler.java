package com.pmp.pensionerdetail.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest req){
		log.info("In Exception ResponseEntity");
		String msg = ex.getMessage()+" only JSON Supported";
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),msg, req.getDescription(false),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
	
	@ExceptionHandler(PensionerDetailsNotFoundException.class)
	public final ResponseEntity<Object> toHandlePensionerDetailsNotFoundException(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PensionerDetailsInvalidParametersException.class)
	public final ResponseEntity<Object> toHandlePensionerDetailsInvalidParameters(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(PensionerDetailsAlreadyFoundException.class)
	public final ResponseEntity<Object> toHandlePensionerDetailsAlreadyFoundException(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_ACCEPTABLE);
	}
	
}
