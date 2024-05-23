package com.foodapp.foodorderingapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class SwaggerConfig{
    @Bean
    public OpenAPI openAPI() {
        Server devServer = new io.swagger.v3.oas.models.servers.Server();
        devServer.setUrl("http://localhost:8086");
        return new OpenAPI().info(new Info().title("Food Ordering App API").version("1.0")).servers(List.of(devServer));
    }
}
