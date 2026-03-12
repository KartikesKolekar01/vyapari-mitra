package com.vyaparimitra.vyapari_mitra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("व्यापारी मित्र API")
                        .description("उधारी व्यवस्थापन प्रणाली - व्यापाऱ्यांसाठी सोपे अॅप")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("व्यापारी मित्र सपोर्ट")
                                .email("support@vyaparimitra.com")
                                .url("https://vyaparimitra.com"))
                        .license(new License()
                                .name("© 2026 व्यापारी मित्र")
                                .url("https://vyaparimitra.com/license")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server"),
                        new Server().url("https://api.vyaparimitra.com").description("Production Server")
                ));
    }
}