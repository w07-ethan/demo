package com.example.demo.dto.request.user;

import com.example.demo.dto.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "User search and pagination request")
public class UserPageRequest extends PageRequest {
    @Schema(description = "Filter by active status", example = "true")
    private Boolean isActive;
}
