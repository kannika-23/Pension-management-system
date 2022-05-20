package com.pmp.pmpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@EnableEurekaServer
@SpringBootApplication
public class PmpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmpServerApplication.class, args);
	}

}
