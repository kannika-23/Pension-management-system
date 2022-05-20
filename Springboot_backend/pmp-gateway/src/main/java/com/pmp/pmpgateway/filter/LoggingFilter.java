package com.pmp.pmpgateway.filter;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.pmp.pmpgateway.exception.JwtTokenMalformedException;
import com.pmp.pmpgateway.exception.JwtTokenMissingException;
import com.pmp.pmpgateway.jwt.JwtUtil;

import io.jsonwebtoken.Claims;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GatewayFilter {
	
	private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("in logging filter -> {}", exchange.getRequest().getPath());
		
		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

		final List<String> apiEndpoints = new ArrayList<String>();
		apiEndpoints.add("pmp/pensioner-auth/register");
		apiEndpoints.add("pmp/pensioner-auth/login");


		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));
		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = exchange.getResponse();
				logger.info("req_isapi -> {}",request);
				response.setStatusCode(HttpStatus.UNAUTHORIZED);

				return response.setComplete();
			}
			
			final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
			

			try {
				jwtUtil.validateToken(token);
				
			} catch (JwtTokenMalformedException | JwtTokenMissingException e) {
				ServerHttpResponse response = exchange.getResponse();
				logger.info("req_isapi catch -> {}",request);
				response.setStatusCode(HttpStatus.BAD_REQUEST);
				return response.setComplete();
			}

			Claims claims = jwtUtil.getAllClaimsFromToken(token);
			logger.info("claims -> {}",claims);
			exchange.getRequest().mutate().header("username", String.valueOf(claims.get("username"))).build();
			logger.info("final -> {}",exchange);
	}
		
		return chain.filter(exchange);
		
	}

}
