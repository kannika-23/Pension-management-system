package com.pmp.processpension.exception;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@JsonIgnoreProperties
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExceptionMessage {
	
	private Date date;
	private String message;
	private String error;
	private String path;

}
