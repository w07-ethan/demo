package com.example.demo.service.user;

import com.example.demo.dto.request.user.CreateUserRequest;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        // Check email exists
        if (userMapper.findByEmail(createUserRequest.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        // Check username exists
        if (userMapper.findByUserName(createUserRequest.username()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(createUserRequest.password());
        User user = new User()
                .setEmail(createUserRequest.email())
                .setUsername(createUserRequest.username())
                .setFirstName(createUserRequest.firstName())
                .setPasswordHash(encodedPassword)
                .setLastName(createUserRequest.lastName());
        userMapper.insert(user);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
