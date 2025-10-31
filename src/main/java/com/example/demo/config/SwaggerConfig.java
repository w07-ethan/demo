package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;

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
            // Add Accept-Language header parameter
            operation.addParametersItem(new HeaderParameter()
                    .name("Accept-Language")
                    .description("Language preference (en for English, vi for Vietnamese)")
                    .required(false)
                    .schema(new StringSchema()
                            ._enum(Arrays.asList("en", "vi"))
                            ._default("en"))
                    .example("en"));

            // Add global 500 error response
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
