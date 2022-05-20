package com.pmp.pensionerdetail.exception;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExceptionMessage {
	
	private Date date;
	private String message;
	private String error;
	private String path;

}
