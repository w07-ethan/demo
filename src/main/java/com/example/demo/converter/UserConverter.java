package com.example.demo.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.example.demo.dto.response.UserVo;
import com.example.demo.model.UserPo;
import com.example.demo.model.enums.ActiveStus;

@Mapper(componentModel = "spring")
public interface UserConverter {
    @Mapping(target = "fullName", source = ".", qualifiedByName = "fullName")
    @Mapping(target = "isActive", source = "isActive")
    UserVo toUserVo(UserPo userPO);

    @Named("fullName")
    default String fullName(UserPo userPO) {
        if (userPO == null) {
            return null;
        }
        String firstName = userPO.getFirstName();
        String lastName = userPO.getLastName();
        if (firstName == null && lastName == null) {
            return null;
        }
        if (firstName == null) {
            return lastName;
        }
        if (lastName == null) {
            return firstName;
        }
        return firstName + " " + lastName;
    }

    default boolean map(ActiveStus status) {
        return status == ActiveStus.ACTIVE;
    }
}
