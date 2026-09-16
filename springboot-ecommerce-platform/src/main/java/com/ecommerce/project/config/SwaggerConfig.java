package com.ecommerce.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /* Swagger UI works only with header based authentication. Following configs are made to enable
       authorization on Swagger (Authorize button in the top right corner) */
    @Bean
    public OpenAPI customOpenAPI(){
        /* This tells the Swagger UI that we want to make use of JWT tokens, and we want to pass it in
           the HTTP authorization header */
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")   // This tells swagger to make use of bearer authentication scheme
                .bearerFormat("JWT")
                .description("JWT Bearer Token");

        // I am going to tell Swagger that above API Scheme is required for all kind of API calls
        SecurityRequirement bearerRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        // Actual OpenAPI object
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot eCommerce APIs")
                        .version("1.0")
                        .description("This is a Spring Boot project for eCommerce")
                        .license(new License().name("Apache 2.0").url("http://apache-maven.com"))
                        .contact(new Contact()
                                .name("Purusharth Rana")
                                .email("pururana1335@gmail.com")
                                .url("http://github.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("http://project.com"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", bearerScheme))
                        .addSecurityItem(bearerRequirement);
    }

}
