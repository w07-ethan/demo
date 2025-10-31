package com.example.demo.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    COD("Cash on Delivery"),
    PAYPAL("PayPal"),
    MOMO("MoMo Wallet");

    private final String description;
}