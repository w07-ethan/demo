package com.example.demo.service;

import com.example.demo.dto.request.user.CreateUserRequest;
import com.example.demo.dto.request.user.UserPageRequest;
import com.example.demo.dto.response.PageVo;
import com.example.demo.dto.response.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.UserPo;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ethan
 * @since 2025-10-23
 */
public interface IUserService extends IService<UserPo> {
    UserVo createUser(CreateUserRequest request);

    PageVo<UserVo> getUsers(@Valid @ParameterObject UserPageRequest request);
}
