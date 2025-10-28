package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@OpenAPIDefinition(
        info = @Info(
                title = "Demo Service API",
                description = "Demo API documentation",
                version = "1.0"
        )
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OperationCustomizer globalResponses() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            operation.getResponses().addApiResponse("400",
                    new ApiResponse()
                            .description("Invalid request parameters (Validation failed)")
                            .content(new Content()
                                    .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                            new MediaType()
                                                    .schema(new Schema<>()
                                                            .$ref("#/components/schemas/AppVo")))));

            operation.getResponses().addApiResponse("409",
                    new ApiResponse()
                            .description("Resource already exists")
                            .content(new Content()
                                    .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                            new MediaType()
                                                    .schema(new Schema<>()
                                                            .$ref("#/components/schemas/ResourceAlreadyExists")))));

            operation.getResponses().addApiResponse("500",
                    new ApiResponse()
                            .description("Internal server error")
                            .content(new Content()
                                    .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                            new MediaType()
                                                    .schema(new Schema<>()
                                                            .$ref("#/components/schemas/InternalServerError")))));

            return operation;
        };
    }

    @Bean
    public OpenApiCustomizer schemaCustomizer() {
        return openApi -> {
            if (openApi.getComponents() == null) {
                openApi.setComponents(new Components());
            }

            // Register AppVo schema
            openApi.getComponents().addSchemas("AppVo",
                    new Schema<>()
                            .type("object")
                            .addProperty("status", new Schema<>()
                                    .type("string")
                                    .example("ERROR"))
                            .addProperty("code", new Schema<>()
                                    .type("integer")
                                    .example(400))
                            .addProperty("message", new Schema<>()
                                    .type("string")
                                    .example("Validation failed"))
                            .addProperty("data", new Schema<>()
                                    .type("object")
                                    .nullable(true))
                            .addProperty("errors", new Schema<>()
                                    .type("object")
                                    .additionalProperties(new Schema<>().type("string"))
                                    .nullable(true)));

            // Register ResourceAlreadyExistsException schema
            openApi.getComponents().addSchemas("ResourceAlreadyExists",
                    new Schema<>()
                            .type("object")
                            .addProperty("status", new Schema<>()
                                    .type("string")
                                    .example("ERROR"))
                            .addProperty("code", new Schema<>()
                                    .type("integer")
                                    .example(409))
                            .addProperty("message", new Schema<>()
                                    .type("string")
                                    .example("Resource already exists")));

            // Register InternalServerError schema
            openApi.getComponents().addSchemas("InternalServerError",
                    new Schema<>()
                            .type("object")
                            .addProperty("status", new Schema<>()
                                    .type("string")
                                    .example("ERROR"))
                            .addProperty("code", new Schema<>()
                                    .type("integer")
                                    .example(500))
                            .addProperty("message", new Schema<>()
                                    .type("string")
                                    .example("Internal server error")));
        };
    }
}
