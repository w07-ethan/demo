package com.example.demo.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActiveStatus {
    ACTIVE(true),
    INACTIVE(false);

    @EnumValue
    @JsonValue
    private final boolean value;
}