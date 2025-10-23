package com.example.demo.controller;

import com.example.demo.dto.request.user.CreateUserRequest;
import com.example.demo.dto.response.AppResponse;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "User API", description = "APIs for managing users")
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new user", description = "Creates a new user account.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AppResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data (Validation failed)",
                    content = @Content(
                            schema = @Schema(implementation = AppResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Resource Conflict (e.g., email already exists)",
                    content = @Content(
                            schema = @Schema(implementation = AppResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = AppResponse.class)
                    )
            )
    })
    public ResponseEntity<AppResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        UserResponse userResponse = userService.createUser(createUserRequest);

        return new ResponseEntity<>(AppResponse.success(
                userResponse,
                "User created successfully"
        ), HttpStatus.CREATED);
    }
}