package com.example.demo.controller;

import com.example.demo.dto.request.user.CreateUserRequestVo;
import com.example.demo.dto.request.user.UserPageRequestVo;
import com.example.demo.dto.response.AppVo;
import com.example.demo.dto.response.PageVo;
import com.example.demo.dto.response.UserVo;
import com.example.demo.service.IUserService;
import com.example.demo.utils.XMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "User API", description = "APIs for managing users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/users")
    @Operation(summary = "Get paginated list of users", description = "Retrieves a paginated list of users with optional search and sorting.")
    public ResponseEntity<AppVo<PageVo<UserVo>>> getUsers(@Valid @ParameterObject UserPageRequestVo request) {
        PageVo<UserVo> users = userService.getUsers(request);

        return ResponseEntity.ok(AppVo.success(users, XMessages.getMessage("user.retrieved.success")));
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new user", description = "Creates a new user account.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "409", description = "User created successfully")
            }
    )
    public ResponseEntity<AppVo<UserVo>> createUser(@Valid @RequestBody CreateUserRequestVo createUserRequest) {
        UserVo userVo = userService.createUser(createUserRequest);

        URI location1 = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userVo.id())
                .toUri();

        return ResponseEntity.created(location1).body(AppVo.created(userVo, XMessages.getMessage("user.created.success")));
    }
}