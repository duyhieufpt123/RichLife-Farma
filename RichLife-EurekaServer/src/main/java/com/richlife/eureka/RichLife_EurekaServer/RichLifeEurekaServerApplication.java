package com.richlife.eureka.RichLife_EurekaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RichLifeEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RichLifeEurekaServerApplication.class, args);
	}

}
