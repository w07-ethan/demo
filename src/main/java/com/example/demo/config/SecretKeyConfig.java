package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.client.RestTemplate;
import com.example.demo.model.PayPalConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecretKeyConfig {
    @Value("${paypal.url-config-key}")
    private String urlConfigKey;
    private final ConfigurableEnvironment environment;

    @EventListener(ApplicationReadyEvent.class)
    @SuppressWarnings("null")
    public void onApplicationReady() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonResponse = restTemplate.getForObject(urlConfigKey, String.class);

            if (jsonResponse == null) {
                log.warn("Received null response from config service at: {}", urlConfigKey);
                return;
            }

            String nonNullJsonResponse =
                    Objects.requireNonNull(jsonResponse, "JSON response cannot be null");
            JSONObject paypalObject =
                    JSONUtil.parseObj(nonNullJsonResponse).getJSONObject("paypal");
            PayPalConfig payPalConfig = JSONUtil.toBean(paypalObject, PayPalConfig.class);
            updateEnvironmentProperties(payPalConfig);
            log.info("PayPal Config loaded: {}", payPalConfig);
        } catch (Exception e) {
            log.error(
                    "Error loading PayPal config from external service: {}. Application will continue with default configuration.",
                    e.getMessage());
        }
    }

    private void updateEnvironmentProperties(PayPalConfig config) {
        Map<String, Object> props = new HashMap<>();
        props.put("paypal.enabled", config.isEnabled());
        props.put("paypal.client-id", config.getClientId());
        props.put("paypal.client-secret", config.getClientSecret());
        props.put("paypal.mode", config.getMode());
        props.put("paypal.return-url", config.getReturnUrl());
        props.put("paypal.cancel-url", config.getCancelUrl());
        props.put("paypal.brand-name", config.getBrandName());

        MapPropertySource propertySource = new MapPropertySource("paypal", props);

        environment.getPropertySources().addFirst(propertySource);

        log.info("PayPal Config updated: {}", environment);
    }
}
