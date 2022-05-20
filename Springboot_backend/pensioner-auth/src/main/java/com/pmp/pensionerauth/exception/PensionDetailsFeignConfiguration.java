package com.pmp.pensionerauth.exception;

import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.ErrorDecoder;

@Configuration
public class PensionDetailsFeignConfiguration extends FeignClientProperties.FeignClientConfiguration{
	
	@Bean
	public ErrorDecoder errorDecoder() {
		System.out.println("in decoderconfig");
		return new CustomErrorDecoder();
	}

}
