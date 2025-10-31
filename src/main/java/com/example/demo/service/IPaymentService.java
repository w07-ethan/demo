package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.PaymentPo;
import com.example.demo.service.payment.handler.PaymentGateway;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ethan
 * @since 2025-10-30
 */
public interface IPaymentService extends IService<PaymentPo> {
}