package cn.tedu.csmall.business.controller;

import cn.tedu.csmall.business.service.IBusinessService;
import cn.tedu.csmall.commons.exception.CoolSharkServiceException;
import cn.tedu.csmall.commons.restful.JsonResult;
import cn.tedu.csmall.commons.restful.ResponseCode;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base/business")
// knife4j介绍当前控制器作用
@Api(tags = "购买业务开始模块")
public class BusinessController {

    @Autowired
    private IBusinessService businessService;

    @PostMapping("/buy") // localhost:20000/base/business/buy
    @ApiOperation("发起购买")
    @SentinelResource(value = "buy",
                blockHandler = "blockError",fallback = "fallbackError")
    public JsonResult buy(){
        // 调用业务逻辑层方法即可
        businessService.buy();
        return JsonResult.ok("购买完成");
    }
    public JsonResult blockError(BlockException e){

        return JsonResult.failed(ResponseCode.BAD_REQUEST,"服务器忙");
    }

    public JsonResult fallbackError(){
        return JsonResult
          .failed(ResponseCode.INTERNAL_SERVER_ERROR,"服务降级");
    }

    @GetMapping("/test")
    @ApiOperation("Aop测试方法")
    public JsonResult aopTest(){
        System.out.println("控制器方法运行");
//        String abc=null;
//        abc.toString();
        return JsonResult.ok("运行完成!");
    }






}
