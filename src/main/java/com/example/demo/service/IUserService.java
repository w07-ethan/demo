package com.example.demo.service;

import com.example.demo.dto.request.user.CreateUserRequest;
import com.example.demo.dto.response.user.UserResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.UserPO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ethan
 * @since 2025-10-23
 */
public interface IUserService extends IService<UserPO> {
    UserResponse createUser(CreateUserRequest request);
}
