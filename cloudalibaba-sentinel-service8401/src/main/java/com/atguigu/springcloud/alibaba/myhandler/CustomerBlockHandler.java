package com.atguigu.springcloud.alibaba.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomerBlockHandler {

    public static CommonResult handleException(BlockException blockException){
        return new CommonResult(444,"按照客户自定义,globakl handleException----------1");
    }


    public static CommonResult handleException2(BlockException blockException){
        return new CommonResult(444,"按照客户自定义,globakl handleException----------2");
    }
}
