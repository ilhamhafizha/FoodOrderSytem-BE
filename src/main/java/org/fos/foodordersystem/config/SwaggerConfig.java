package org.fos.foodordersystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Muhammad Ilham Hafizha",
                        email = "muhammadilhamhafizha@gmail.com"
                ),
                description = "Swagger documentation for Aplikasi Food Order System",
                title = "Swagger Aplikasi Food Order Sytem",
                version = "1.0.0",
                license = @License(
                        name = "Food Order System",
                        url = "https://github.com/ilhamhafizha/MuhammadIlhamHafizha_TugasAkhir_FoodOrderSystem"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Development",
                        url = "http://localhost:8080/"
                ),
                @Server(
                        description = "Staging",
                        url = "http://localhost:8080/"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
