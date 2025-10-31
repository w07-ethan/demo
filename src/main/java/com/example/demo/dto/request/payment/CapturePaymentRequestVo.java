package com.example.demo.dto.request.payment;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CapturePaymentRequestVo {
    @NotNull(message = "Token is required")
    private String token;

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String key, Object value) {
        additionalProperties.put(key, value);
    }
}
