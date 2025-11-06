package com.example.demo.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class XMessages {
    private static MessageSource messageSource;

    public XMessages(MessageSource messageSource) {
        XMessages.messageSource = messageSource;
    }

    public static String getMessage(String messageKey, Object... args) {
        try {
            return messageSource.getMessage(messageKey == null ? "" : messageKey, args,
                    LocaleContextHolder.getLocale());
        } catch (Exception ex) {
            return messageKey;
        }
    }
}
