package com.example.demo.service.impl;

import com.example.demo.dto.request.user.CreateUserRequest;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserPO;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ethan
 * @since 2025-10-23
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        // Check email exists
        if (userMapper.findByEmail(request.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        // Check username exists
        if (userMapper.findByUserName(request.username()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        UserPO userPO = new UserPO()
                .setEmail(request.email())
                .setUsername(request.username())
                .setFirstName(request.firstName())
                .setPasswordHash(encodedPassword)
                .setLastName(request.lastName());
        userMapper.insert(userPO);

        return new UserResponse(
                userPO.getId(),
                userPO.getUsername(),
                userPO.getEmail(),
                userPO.getFirstName(),
                userPO.getLastName(),
                userPO.getCreatedAt(),
                userPO.getUpdatedAt()
        );
    }
}
