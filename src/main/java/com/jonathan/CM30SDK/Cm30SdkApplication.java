package com.jonathan.CM30SDK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Cm30SdkApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Cm30SdkApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Cm30SdkApplication.class);
	}
}
