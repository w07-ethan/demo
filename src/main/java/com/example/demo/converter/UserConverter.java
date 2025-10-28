package com.example.demo.converter;

import com.example.demo.dto.response.UserVo;
import com.example.demo.model.enums.ActiveStatus;
import com.example.demo.model.UserPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {
    @Mapping(target = "fullName", expression = "java(userPO.getFirstName() + \" \" + userPO.getLastName())")
    UserVo toUserVo(UserPo userPO);

    default boolean map(ActiveStatus status) {
        return status == ActiveStatus.ACTIVE;
    }
}