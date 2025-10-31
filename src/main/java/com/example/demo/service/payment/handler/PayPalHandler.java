package com.example.demo.service.payment.handler;

import com.example.demo.consts.PaymentConst;
import com.example.demo.dto.request.payment.CapturePaymentRequestVo;
import com.example.demo.dto.request.payment.CreatePaymentRequestVo;
import com.example.demo.dto.response.payment.CapturePaymentResponseVo;
import com.example.demo.dto.response.payment.CreatePaymentResponseVo;
import com.example.demo.model.enums.PaymentMethod;
import com.example.demo.model.enums.PaymentStus;
import com.example.demo.utils.XMap;
import com.paypal.sdk.models.CheckoutPaymentIntent;
import com.paypal.sdk.models.Order;
import com.w07.extn.payment.paypal.exception.PaypalBusinessException;
import com.w07.extn.payment.paypal.service.PayPalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayPalHandler implements PaymentGateway {
    @Value("${paypal.return-url}")
    private String RETURN_URL;
    @Value("${paypal.cancel-url}")
    private String CANCEL_URL;
    @Value("${paypal.brand-name}")
    private String BRAND_NAME;
    @Value("${paypal.enabled}")
    private boolean enabled;

    private final PayPalService payPalService;

    @Override
    public String getGatewayName() {
        return PaymentMethod.PAYPAL.name();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public CreatePaymentResponseVo initPayment(CreatePaymentRequestVo createPaymentRequestVo) {
        Map<String, Object> request = prepareInitRequest(createPaymentRequestVo);
        Map<String, Object> response = payPalService.createPayment(request);

        validateResponse(response);
        Order order = XMap.get(response, "order", Order.class);
        validateOrder(order);

        String status = XMap.get(response, "status", String.class, PaymentConst.DEFAULT_STATUS_RESPONSE);
        String redirectUrl = XMap.get(response, "approvalUrl", String.class, PaymentConst.DEFAULT_URL_APPROVAL);

        return CreatePaymentResponseVo.builder()
                .status(status)
                .paymentId(order.getId())
                .redirectUrl(redirectUrl)
                .build();
    }

    @Override
    public CapturePaymentResponseVo capturePayment(CapturePaymentRequestVo capturePaymentRequestVo) {
        Map<String, Object> request = prepareCaptureRequest(capturePaymentRequestVo);
        Map<String, Object> response = payPalService.capturePayment(request);

        validateResponse(response);

        Order order = XMap.get(response, "order", Order.class);
        validateOrder(order);

        String status = order.getStatus().value();
        BigDecimal totalCapturedAmount = order.getPurchaseUnits().stream()
                .flatMap(unit -> unit.getPayments().getCaptures().stream())
                .map(capture -> new BigDecimal(capture.getAmount().getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CapturePaymentResponseVo.builder()
                .orderId(order.getId())
                .checkoutId("1") // This would be dynamically generated
                .amount(totalCapturedAmount)
                .gatewayTransactionId(order.getId())
                .paymentMethod(PaymentMethod.PAYPAL)
                .paymentStatus(status.equalsIgnoreCase("COMPLETED") ? PaymentStus.COMPLETED : PaymentStus.CANCELLED)
                .failureMessage(status.equalsIgnoreCase("COMPLETED") ? null : "Payment not completed successfully")
                .build();
    }

    private Map<String, Object> prepareInitRequest(CreatePaymentRequestVo createPaymentRequestVo) {
        Map<String, Object> payPalRequest = new HashMap<>();
        Map<String, Object> additionalProperties = createPaymentRequestVo.getAdditionalProperties();

        payPalRequest.put("totalPrice", createPaymentRequestVo.getTotalPrice());
        payPalRequest.put("checkoutId", "1"); // This would be dynamically generated
        payPalRequest.put("intent", CheckoutPaymentIntent.CAPTURE);
        payPalRequest.put("currencyCode", XMap.get(additionalProperties, "currencyCode", String.class, PaymentConst.DEFAULT_CURRENCY_CODE));
        payPalRequest.put("returnUrl", RETURN_URL);
        payPalRequest.put("cancelUrl", CANCEL_URL);
        payPalRequest.put("brandName", BRAND_NAME);

        return payPalRequest;
    }

    private Map<String, Object> prepareCaptureRequest(CapturePaymentRequestVo capturePaymentRequestVo) {
        Map<String, Object> payPalRequest = new HashMap<>();
        payPalRequest.put("token", capturePaymentRequestVo.getToken());
        return payPalRequest;
    }

    private void validateResponse(Map<String, Object> response) {
        Integer code = XMap.get(response, "code", Integer.class, 0);
        if (code != 200 && code != 201) {
            throw new PaypalBusinessException(
                    XMap.get(response, "message", String.class, "Unknown error"),
                    code
            );
        }
    }

    private void validateOrder(Order order) {
        if (order == null || order.getId() == null) {
            throw new PaypalBusinessException(
                    "Invalid order data received from PayPal",
                    HttpStatus.NOT_FOUND.value()
            );
        }
    }
}