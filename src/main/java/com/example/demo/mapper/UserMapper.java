package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByUserName(@Param("username") String username);
}