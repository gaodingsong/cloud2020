package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {



    @Value("${server.port}")
    private String serverPort;

    @Resource
    private PaymentService paymentService;




    @PostMapping("payment/create")
    public CommonResult create(@RequestBody Payment payment){

        int i = paymentService.create(payment);


        if (i>0){
            return new CommonResult(200,"插入数据源成功,serverPort: "+serverPort,i);
        }
        return new CommonResult(444,"插入数据源失败",i);
    }

    @GetMapping("payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){

        Payment result = paymentService.getPaymentById(id);


        if (result != null){
            return new CommonResult(200,"查询数据源成功,serverPort: "+serverPort,result);
        }
        return new CommonResult(444,"查询没有对应记录，id："+id,result);
    }



    @GetMapping("/payment/lb")
    public String getPaymentLB(){

        return serverPort;
    }


}
