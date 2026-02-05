package com.example.project.dto.response;

import java.util.Map;

public class ApiResponse<T> {

    private String status;
    private T payload;
    private Map<String, Object> error;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.status = "SUCCESS";
        res.payload = data;
        return res;
    }


    public static ApiResponse<Void> error(Object details, String code) {
        ApiResponse<Void> res = new ApiResponse<>();
        res.status = "ERROR";
        res.error = Map.of(
                "code", code,
                "details", details
        );
        return res;
    }


    public String getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }

    public Map<String, Object> getError() {
        return error;
    }
}
