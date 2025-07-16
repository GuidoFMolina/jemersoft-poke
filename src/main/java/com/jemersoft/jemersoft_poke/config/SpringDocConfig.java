package com.jemersoft.jemersoft_poke.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class SpringDocConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("pokemons")
                .pathsToMatch("/pokemon/**")
                .build();
    }
}
