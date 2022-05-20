 package com.pmp.processpension.exception;


import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
	
	@Override
	public Exception decode(String methodKey, Response response) {
		log.info("in Custom Error Decoder");

		String msg =  response.body().toString();
		return new PensionerDetailsExceptionFeign(msg.substring(msg.indexOf("message")+10, msg.indexOf("error")-3));
		
	}
}
