package com.example.demo.dto.response.payment;

import com.example.demo.model.enums.PaymentMethod;
import com.example.demo.model.enums.PaymentStus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CapturePaymentResponseVo(
        String orderId,
        String checkoutId,
        BigDecimal amount,
        String gatewayTransactionId,
        PaymentMethod paymentMethod,
        PaymentStus paymentStatus,
        String failureMessage) {
}