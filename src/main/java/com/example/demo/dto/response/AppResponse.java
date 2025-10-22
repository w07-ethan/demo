package com.example.demo.dto.response;

import com.example.demo.enums.ApiStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse<T> {
    private final ApiStatus status;
    private final int code;
    private final String message;
    private final T data;
    private final Map<String, String> errors;

    // Private constructor to force use of static factory methods
    private AppResponse(ApiStatus status, int code, String message, T data, Map<String, String> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public static <T> AppResponse<T> success(T data, String message) {
        return new AppResponse<>(ApiStatus.SUCCESS, 200, message, data, null);
    }

    public static <T> AppResponse<T> success(T data) {
        return success(data, "Request was successful");
    }

    public static <T> AppResponse<T> created(T data, String message) {
        return new AppResponse<>(ApiStatus.SUCCESS, 201, message, data, null);
    }

    public static <T> AppResponse<T> error(int code, String message, Map<String, String> errors) {
        return new AppResponse<>(ApiStatus.ERROR, code, message, null, errors);
    }

    public static <T> AppResponse<T> error(int code, String message) {
        return new AppResponse<>(ApiStatus.ERROR, code, message, null, null);
    }
}
