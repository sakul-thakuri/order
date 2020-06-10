package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.order.controller"))
				.paths(PathSelectors.ant("/orders/**"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails () {
		return new ApiInfo(
				"Order Management",
				" Apis for Order management",
				"1.0",
				"free to use",
				new springfox.documentation.service.Contact("sakul thakuri","#","sazzrulz38@gmail.com"),
				"API license",
				"#",
				Collections.emptyList());
	}
}
