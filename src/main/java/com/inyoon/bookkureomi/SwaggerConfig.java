package com.inyoon.bookkureomi;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	//Swagger
	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)          
	    	.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build()
	        .apiInfo(apiInfo());
	}
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "북꾸러미's REST API",		//title
	      "In&Yoon custom description of API.",		//description
	      "1.0",	//version
	      "https://github.com/im57/bookkureomi", 	//termsOfServiceUrl
	      new Contact("Im", "https://github.com/im57/bookkureomi", "eovhehd1986@gmail.com"),		//contact 
	      "ⓒ InyOon",		//license
	      "http://localhost:8000",	//licenseUrl
	      Collections.emptyList());		//vendorExtensions
	}
}
