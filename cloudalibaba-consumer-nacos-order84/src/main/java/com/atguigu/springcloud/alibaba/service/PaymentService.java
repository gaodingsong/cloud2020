package com.atguigu.springcloud.alibaba.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nocas-payment-provider",fallback = PaymentFallbackservice.class)
public interface PaymentService {
//
//    @GetMapping("/paymentSql/{id}")
//    public CommonResult<Payment> paymentSql (@PathVariable("id" ) Long id);
//

    @GetMapping("/paymentSql/{id}")
    public CommonResult<Payment> paymentSql(@PathVariable("id") Long id );
}
