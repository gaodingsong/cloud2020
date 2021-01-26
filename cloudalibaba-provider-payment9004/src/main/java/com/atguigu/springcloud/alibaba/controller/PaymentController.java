package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {


    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap =new HashMap<>();
    static {
        hashMap.put(1L,new Payment(1L,"serial001"));
        hashMap.put(1L,new Payment(2L,"serial002"));
        hashMap.put(1L,new Payment(3L,"serial003"));
    }

    @GetMapping("/paymentSql/{id}")
    public CommonResult<Payment> paymentSql(@PathVariable("id") Long id ){
        Payment payment = hashMap.get(id);
        CommonResult<Payment> commonResult = new CommonResult(200, "from mysql,serverPort" + serverPort, payment);
        return commonResult;
    }

}
