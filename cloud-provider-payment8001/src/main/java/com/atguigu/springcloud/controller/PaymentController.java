package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;





    @PostMapping("payment/create")
    public CommonResult create(@RequestBody Payment payment){

        int i = paymentService.create(payment);


        if (i>0){
            return new CommonResult(200,"插入数据源成功,serverPort: "+serverPort,i);
        }
        return new CommonResult(444,"插入数据源失败",i);
    }

    @GetMapping("payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        if (result != null){
            return new CommonResult(200,"查询数据源成功,serverPort: "+serverPort,result);
        }
        return new CommonResult(444,"查询没有对应记录，id："+id,result);
    }

    /**
     * 服务发现
     * @return
     */
    @GetMapping("payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("****服务列表：{}",service);
        }
        List<ServiceInstance> cloud_payment_service = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance serviceInstance : cloud_payment_service) {

            log.info("服务ID：{}，：主机IP:{},端口号：{}，“访问路径：{}",serviceInstance.getInstanceId(),serviceInstance.getHost(),serviceInstance.getPort(),serviceInstance.getUri());
        }
        return this.discoveryClient;
    }


    @GetMapping("/payment/lb")
    public String getPaymentLB(){

        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return serverPort;
    }
    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){

        return "hi,this is zipkin";
    }


}
