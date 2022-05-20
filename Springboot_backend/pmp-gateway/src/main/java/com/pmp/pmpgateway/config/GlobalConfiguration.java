package com.pmp.pmpgateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pmp.pmpgateway.filter.LoggingFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GlobalConfiguration {
	
	@Autowired
	private LoggingFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		log.info("in global config");
		return builder.routes()
				.route(r -> r.path("/pmp/pensioner-auth/**").filters(f -> f.filter(filter)).uri("lb://pensioner-auth"))
				.route(r -> r.path("/pmp/pensioner-details/**").filters(f -> f.filter(filter)).uri("lb://pensioner-details"))
				.route(r -> r.path("/pmp/process-pension/**").filters(f -> f.filter(filter)).uri("lb://process-pension"))
				.build();
	}

}