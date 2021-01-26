package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakercontroller {

    @Resource
    private PaymentService paymentService;

    @Resource
    private RestTemplate restTemplate;


    @Value("${service-url.nacos-user-service}")
    private String serviceUrl;


    @GetMapping("consumer/fallback/{id}")
//    @SentinelResource(value = "fallback")
//    @SentinelResource(value = "fallback",fallback = "handlerFallback")// 只負責業務異常
//    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler")// 既有fallback  也有blockHandler
    @SentinelResource(value = "fallback"
            ,fallback = "handlerFallback"
            ,blockHandler = "blockHandler"
            ,exceptionsToIgnore =IllegalArgumentException.class )// 忽略異常
    public CommonResult<Payment> paymentInfo(@PathVariable("id") Long id ){
        CommonResult<Payment> result = restTemplate.getForObject(serviceUrl + "/paymentSql/" + id, CommonResult.class);
        if (id == 4 ){
            throw new  IllegalArgumentException("IllegalArgumentException   非法參數異常");
        }else if (result.getData() == null){
            throw new NullPointerException("NullPointerException 該ID 沒有對應記錄，空指針異常");
        }
        return result;
    }

    //  注意：若blockHandler和fallback都进行了配置,j则被限流降级而抛出BlockException时只会进入blockHandler处理逻辑。


    //  本例子是fallback
    public CommonResult handlerFallback(@PathVariable Long id,Throwable throwable){
        Payment p = new Payment(id,null);
        return  new CommonResult(444,"兜底異常handlerFallback內容"+throwable.getMessage(),p);


    }

    // 本例是blockHandler
    public CommonResult blockHandler(@PathVariable Long id, BlockException blockException){
        Payment p = new Payment(id,"null");
        return  new CommonResult(444,"blockHandler -sentinel限流，無此流水"+blockException.getMessage(),p);


    }


    @GetMapping(value = "/consumer/paymentSql/{id}")
    public CommonResult<Payment> paymentSql (@PathVariable("id" ) Long id){
        return paymentService.paymentSql(id);
    }
}
