package com.app.audienceize.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                name = "Piyush Gupta",
                email = "guptapiyush238@gmail.com",
                url = "https://www.linkedin.com/in/piyushgupta238"
        ),
        description = "API Documentation for Movie ticket booking system",
        title = "Movie ticket Booking System - API Documentation",
        version = "1.0",
        termsOfService = "Terms of Service"),
        security = {@SecurityRequirement(name = "BearerAuth")}
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "BearerAuth",
        description = "JWT Authentication description",
        scheme = "Bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfiguration {
}
