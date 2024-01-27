package com.orange.orangeportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrangeportfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrangeportfolioApplication.class, args);
	}

}
