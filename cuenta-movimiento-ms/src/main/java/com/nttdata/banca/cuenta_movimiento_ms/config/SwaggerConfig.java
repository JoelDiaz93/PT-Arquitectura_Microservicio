package com.nttdata.banca.cuenta_movimiento_ms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("API Cuentas y Movimientos")
                .version("1.0.0")
                .description("Microservicio para gestión de cuentas y movimientos"));
    }
}
