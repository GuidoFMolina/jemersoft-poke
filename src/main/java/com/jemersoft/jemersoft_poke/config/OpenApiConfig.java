package com.jemersoft.jemersoft_poke.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jemersoft Poke API")
                        .version("v1.0")
                        .description("API for managing Pokémon data")
                        .contact(new Contact()
                                .name("Guido Molina")
                                .email("guidoo840@gmail.com")
                                .url("https://github.com/GuidoFMolina"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }

}
