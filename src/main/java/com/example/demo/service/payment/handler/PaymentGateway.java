package com.example.demo.service.payment.handler;

import com.example.demo.dto.request.payment.CapturePaymentRequestVo;
import com.example.demo.dto.request.payment.CreatePaymentRequestVo;
import com.example.demo.dto.response.payment.CapturePaymentResponseVo;
import com.example.demo.dto.response.payment.CreatePaymentResponseVo;

public interface PaymentGateway {
    String getGatewayName();

    boolean isEnabled();

    CreatePaymentResponseVo initPayment(CreatePaymentRequestVo initPaymentRequestVo);

    CapturePaymentResponseVo capturePayment(CapturePaymentRequestVo capturePaymentRequestVo);
}