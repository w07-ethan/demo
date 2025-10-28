package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.UserPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ethan
 * @since 2025-10-23
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPo> {
    Optional<UserPo> findByEmail(String email);

    Optional<UserPo> findByUserName(String username);
}

