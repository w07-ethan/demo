package com.example.demo.model;

import lombok.Data;

@Data
public class PayPalConfig {
    private boolean enabled;
    private String clientId;
    private String clientSecret;
    private String mode;
    private String returnUrl;
    private String cancelUrl;
    private String brandName;
}
