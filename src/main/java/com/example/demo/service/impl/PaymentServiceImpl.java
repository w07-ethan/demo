package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dto.request.payment.CapturePaymentRequestVo;
import com.example.demo.dto.request.payment.CreatePaymentRequestVo;
import com.example.demo.dto.response.payment.CapturePaymentResponseVo;
import com.example.demo.dto.response.payment.CreatePaymentResponseVo;
import com.example.demo.mapper.PaymentMapper;
import com.example.demo.model.PaymentPo;
import com.example.demo.service.IPaymentService;
import com.example.demo.service.payment.handler.PaymentGateway;
import com.example.demo.service.payment.handler.PaymentGatewayFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentPo> implements IPaymentService {
    private final PaymentGatewayFactory paymentGatewayFactory;

    private PaymentGateway getPaymentGateway(String gatewayName) {
        PaymentGateway gateway = paymentGatewayFactory.getGateway(gatewayName);
        if (gateway == null) {
            throw new IllegalArgumentException("No active gateway found for: " + gatewayName);
        }

        return gateway;
    }

    public CreatePaymentResponseVo initPayment(CreatePaymentRequestVo createPaymentRequestVo) {
        PaymentGateway paymentGateway = getPaymentGateway(createPaymentRequestVo.getPaymentMethod());

        return paymentGateway.initPayment(createPaymentRequestVo);
    }

    public CapturePaymentResponseVo capturePayment(CapturePaymentRequestVo capturePaymentRequestVo) {
        PaymentGateway paymentGateway = getPaymentGateway(capturePaymentRequestVo.getPaymentMethod());

        return paymentGateway.capturePayment(capturePaymentRequestVo);
    }
}
