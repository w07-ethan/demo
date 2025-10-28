package com.example.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated response wrapper")
public class PageVo<T> {
    @Schema(description = "List of items")
    private List<T> content;

    @Schema(description = "Current page number")
    private int page;

    @Schema(description = "Page size")
    private int size;

    @Schema(description = "Total elements")
    private long totalElements;

    @Schema(description = "Total pages")
    private long totalPages;

    @Schema(description = "Is first page")
    private boolean first;

    @Schema(description = "Is last page")
    private boolean last;
}