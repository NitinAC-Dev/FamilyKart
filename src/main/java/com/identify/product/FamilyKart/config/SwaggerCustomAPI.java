package com.identify.product.FamilyKart.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerCustomAPI {



    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme=new SecurityScheme();
        SecurityScheme type = securityScheme.type(SecurityScheme.Type.HTTP);
        SecurityScheme bearerFormat = type.scheme("bearer");
        SecurityScheme description = bearerFormat.bearerFormat("JWT");
        SecurityScheme jwtBearerTokenAuthentication = description.description("JWT bearer token authentication");


       /* return new OpenAPI()
                .schemaRequirement("Bearer Authentication",description);*/

        SecurityRequirement securityRequirement=new SecurityRequirement();
        securityRequirement.addList("Bearer Authentication");




        OpenAPI openAPI = new OpenAPI();
        Info info = new Info()
                .title("FamilyKart API Documentation")
                .description("API documentation for FamilyKart application")
                .version("1.0.0").license(new License().name("Apache 2.0").url("http://springdoc.org"))
                .contact(new Contact().name("Nitin AC")
                        .email("sagar.nitin08@gmail.com")
                        .url("github.com/NitinAC08"));
        openAPI.info(info);
        Components components = new Components();

        Components bearerAuthentication = components
                .addSecuritySchemes("Bearer Authentication", jwtBearerTokenAuthentication);
        openAPI.components(bearerAuthentication);
        openAPI.addSecurityItem(securityRequirement);

        return openAPI;

    }
}
