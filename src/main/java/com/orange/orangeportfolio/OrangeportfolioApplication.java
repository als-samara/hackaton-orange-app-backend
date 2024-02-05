package com.orange.orangeportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.orange.orangeportfolio.client")
@SpringBootApplication
public class OrangeportfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrangeportfolioApplication.class, args);
	}

}
