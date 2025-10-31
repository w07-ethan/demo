package com.example.demo.controller;

import com.example.demo.dto.request.payment.CapturePaymentRequestVo;
import com.example.demo.dto.request.payment.CreatePaymentRequestVo;
import com.example.demo.dto.response.AppVo;
import com.example.demo.dto.response.payment.CapturePaymentResponseVo;
import com.example.demo.dto.response.payment.CreatePaymentResponseVo;
import com.example.demo.service.IPaymentService;
import com.example.demo.service.impl.PaymentServiceImpl;
import com.example.demo.service.payment.handler.PaymentGateway;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "APIs for managing users")
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentServiceImpl paymentService;

    @PostMapping(value = "/init")
    @Operation(summary = "Initialize a payment", description = "Initializes a new payment transaction.")
    public ResponseEntity<AppVo<CreatePaymentResponseVo>> initPayment(@Valid @RequestBody CreatePaymentRequestVo request) {
        CreatePaymentResponseVo createPaymentResponseVo = paymentService.initPayment(request);

        return ResponseEntity.ok(AppVo.success(createPaymentResponseVo));
    }

    @PostMapping(value = "/capture")
    @Operation(summary = "Capture a payment", description = "Captures an authorized payment transaction.")
    public ResponseEntity<AppVo<CapturePaymentResponseVo>> capturePayment(
            @Valid @RequestBody CapturePaymentRequestVo capturePaymentRequestVo
    ) {
        CapturePaymentResponseVo createPaymentResponseVo = paymentService.capturePayment(capturePaymentRequestVo);

        return ResponseEntity.ok(AppVo.success(createPaymentResponseVo));
    }
}
