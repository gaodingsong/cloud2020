package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
//    @LoadBalanced  //使用@LoadBalanced注解赋予RestTemplate负载均衡能力  这是因为多个提供者注册到erueka之后  通过服务名调用 不知道分配给哪个
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
