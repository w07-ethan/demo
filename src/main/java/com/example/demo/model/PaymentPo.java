package com.example.demo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.demo.model.enums.PaymentMethod;
import com.example.demo.model.enums.PaymentStus;

import java.math.BigDecimal;

public class PaymentPo extends BasePo<PaymentPo> {
    @TableField("order_id")
    private String orderId;

    @TableField("checkout_id")
    private String checkoutId;

    @TableField
    private BigDecimal amount;

    @TableField("payment_method")
    private PaymentMethod paymentMethod;

    @TableField("payment_status")
    private PaymentStus paymentStus;
}
