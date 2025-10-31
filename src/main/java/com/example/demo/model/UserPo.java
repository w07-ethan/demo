package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.*;
import com.example.demo.model.enums.ActiveStus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author ethan
 * @since 2025-10-23
 */
@Getter
@Setter
@ToString
@TableName("users")
@Accessors(chain = true)
public class UserPo extends BasePo<UserPo> {
    @TableField("username")
    private String username;

    @TableField("email")
    private String email;

    @TableField("password_hash")
    private String passwordHash;

    @TableField("first_name")
    private String firstName;

    @TableField("last_name")
    private String lastName;

    @TableField("is_active")
    private ActiveStus isActive = ActiveStus.INACTIVE;
}
