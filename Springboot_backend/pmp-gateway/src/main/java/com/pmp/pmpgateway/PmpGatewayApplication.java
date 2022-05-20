package com.pmp.pmpgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class PmpGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmpGatewayApplication.class, args);
	}
	

}
