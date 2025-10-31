package com.example.demo.dto.response.payment;

import lombok.Builder;

@Builder
public record CreatePaymentResponseVo(
        String status,
        String paymentId,
        String redirectUrl
) {
}