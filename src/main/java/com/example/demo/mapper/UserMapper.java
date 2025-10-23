package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.User;

import java.util.Optional;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ethan
 * @since 2025-10-23
 */
public interface UserMapper extends BaseMapper<User> {
    Optional<User> findByUserName(String name);

    Optional<User> findByEmail(String email);
}