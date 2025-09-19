package com.learn.springboot_mongodb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My springBoot-mongodb API")
                        .version("1.0")
                        .description("This is API documentation for my springBoot- mongodb API project")
                        .contact(new Contact()
                                .name("Robert Alataf")
                                .email("robert.alataf@google.com")));
    }
}
