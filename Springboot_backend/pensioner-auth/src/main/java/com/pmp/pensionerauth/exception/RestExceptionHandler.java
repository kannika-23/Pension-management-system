package com.pmp.pensionerauth.exception;

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


	@ExceptionHandler(PensionerDetailsInvalidParameters.class)
	public final ResponseEntity<Object> toHandlePensionerDetailsInvalidParameters(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(PensionerDetailsNotFoundException.class)
	public final ResponseEntity<Object> toHandlePensionerDetailNotMatchingException(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(PensionerDetailsExceptionFeign.class)
	public final ResponseEntity<Object> toHandlePensionerDetailsExceptionFeign(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		System.out.println(ex.getMessage());
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
	}  
	
	@ExceptionHandler(JwtTokenMissingException.class)
	public final ResponseEntity<Object> toHandleJwtTokenMissingException(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(JwtTokenMalformedException.class)
	public final ResponseEntity<Object> toHandleJwtTokenMalformedException(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(DisabledException.class)
	public final ResponseEntity<Object> toHandleDisabledException(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public final ResponseEntity<Object> toHandleBadCredentialsException(Exception ex, WebRequest req){
		log.info("In Exception ResponseEntity");
		ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(),ex.getMessage(), ex.getClass().getCanonicalName(),((ServletWebRequest) req).getRequest().getServletPath());
		return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
}
