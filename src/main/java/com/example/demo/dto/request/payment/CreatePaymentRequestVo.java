package com.example.demo.dto.request.payment;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class CreatePaymentRequestVo {
    @NotNull(message = "{validation.totalPrice.required}")
    private BigDecimal totalPrice;

    @NotNull(message = "{validation.paymentMethod.required}")
    private String paymentMethod;

    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String key, Object value) {
        additionalProperties.put(key, value);
    }
}