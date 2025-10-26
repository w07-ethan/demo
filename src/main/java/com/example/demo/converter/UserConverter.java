package com.example.demo.converter;

import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.model.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "")
    UserResponse toResponse(UserPO userPO);
}
