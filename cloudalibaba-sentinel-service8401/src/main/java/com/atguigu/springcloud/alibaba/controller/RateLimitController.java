package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.myhandler.CustomerBlockHandler;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class RateLimitController {


    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handException")
    public CommonResult byResource(){
        return new CommonResult(200,"按照资源名称限流测试ok",new Payment(1L,""));
    }

    public  CommonResult handException(BlockException blockException){
        return new CommonResult(444,blockException.getClass().getCanonicalName(),"服务不可用");
    }

    @GetMapping("/rate/limit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200,"按照URL限流测试ok",new Payment(1L,""));
    }



    @GetMapping("rate/limit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handleException2")
    public CommonResult customerBlockHandler(){
        return new CommonResult(200,"按照客户自定义限流测试ok",new Payment(1L,"serial003"));
    }

}



