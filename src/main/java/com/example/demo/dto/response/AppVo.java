package com.example.demo.dto.response;

import com.example.demo.model.enums.ApiStus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppVo<T> {
    private final ApiStus status;
    private final int code;
    private final String message;
    private final T data;
    private final Map<String, String> errors;

    // Private constructor to force use of static factory methods
    private AppVo(ApiStus status, int code, String message, T data, Map<String, String> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public static <T> AppVo<T> success(T data, String message) {
        return new AppVo<>(ApiStus.SUCCESS, 200, message, data, null);
    }

    public static <T> AppVo<T> success(T data) {
        return success(data, "Request was successful");
    }

    public static <T> AppVo<T> created(T data, String message) {
        return new AppVo<>(ApiStus.SUCCESS, 201, message, data, null);
    }

    public static <T> AppVo<T> error(int code, String message, Map<String, String> errors) {
        return new AppVo<>(ApiStus.ERROR, code, message, null, errors);
    }

    public static <T> AppVo<T> error(int code, String message) {
        return new AppVo<>(ApiStus.ERROR, code, message, null, null);
    }
}
