package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "Base pagination and sorting request")
public class PageRequest {
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final String DEFAULT_SORT_DIRECTION = "desc";

    @Parameter(
            name = "page",
            description = "Page number (0-based)",
            example = "0",
            in = ParameterIn.QUERY
    )
    @Schema(description = "Page number (0-based)", example = "0")
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    private int page = DEFAULT_PAGE_NUMBER;

    @Parameter(
            name = "size",
            description = "Number of items per page",
            example = "10",
            in = ParameterIn.QUERY
    )
    @Schema(description = "Page size", example = "10")
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private int size = DEFAULT_PAGE_SIZE;

    @Parameter(
            name = "sortBy",
            description = "Field to sort by",
            example = "created_at",
            in = ParameterIn.QUERY
    )
    @Schema(description = "Sort field", example = "created_at")
    private String sortBy = DEFAULT_SORT_BY;

    @Parameter(
            name = "sortDirection",
            description = "Sort direction (asc or desc)",
            example = "desc",
            in = ParameterIn.QUERY
    )
    @Schema(description = "Sort direction (asc or desc)", example = "desc")
    private String sortDirection = DEFAULT_SORT_DIRECTION;

    @Parameter(
            name = "search",
            description = "Search keyword",
            in = ParameterIn.QUERY
    )
    @Schema(description = "Search keyword")
    private String q;
}
