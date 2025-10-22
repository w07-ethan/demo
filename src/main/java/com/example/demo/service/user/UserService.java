package com.example.demo.service.user;

import com.example.demo.dto.request.user.CreateUserRequest;
import com.example.demo.dto.response.user.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest createUserRequest);
}
