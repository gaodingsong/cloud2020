package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {



    //==========服务降级
    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池： "+ Thread.currentThread().getName()+" paymentInfo_ok,id" + id;
    }

    /**
     * 异常访问
     * @param id
     * @return
     */

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){

        int a= 10/0;

        int timeNUm =3;
        try {
            TimeUnit.SECONDS.sleep(timeNUm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+ Thread.currentThread().getName()+" paymentInfo_TimeOut,id" + id+",耗时"+timeNUm+"s";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+ Thread.currentThread().getName()+" 系统繁忙或者系统报错,id" + id+",此方法是兜底方法";
    }


        // 服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback" ,commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),// 时间窗口期/时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id ){
        if ( id < 0 ){
            throw new RuntimeException("******id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"调用成功，流水号："+serialNumber;
    }




    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id ){
       return  "******id 不能为负数,请稍后再试";

    }


}
