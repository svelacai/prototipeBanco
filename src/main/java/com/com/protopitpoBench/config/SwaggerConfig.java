package com.com.protopitpoBench.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.com.protopitpoBench.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("Api rest Banco")
	            .description("api rest para el cálculo de la posición de una nave")
	            .termsOfServiceUrl("https://caracaicedo.github.io/protopitpoBench/swagger.json") // Cambia la URL a la ubicación de tu archivo Swagger en GitHub Pages
	            .version("1.0")
	            .build();
	}


	private ApiKey apiKey() {
	    return new ApiKey("jwtToken", "Authorization", "header");
	}
}