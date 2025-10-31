package com.example.demo.service.payment.handler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentGatewayFactory {
    private final List<PaymentGateway> allGateways;
    private Map<String, PaymentGateway> activeGateways;

    @PostConstruct
    private void buildGatewayMap() {
        activeGateways = allGateways.stream()
                .filter(PaymentGateway::isEnabled)
                .collect(Collectors.toMap(PaymentGateway::getGatewayName, Function.identity()));
    }

    public PaymentGateway getGateway(String gatewayName) {
        PaymentGateway gateway = activeGateways.get(gatewayName.toUpperCase());
        if (gateway == null) {
            throw new IllegalArgumentException("No active gateway found for: " + gatewayName);
        }
        return gateway;
    }
}
