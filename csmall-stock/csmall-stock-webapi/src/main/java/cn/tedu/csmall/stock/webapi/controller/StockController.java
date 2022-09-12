package cn.tedu.csmall.stock.webapi.controller;

import cn.tedu.csmall.commons.exception.CoolSharkServiceException;
import cn.tedu.csmall.commons.pojo.stock.dto.StockReduceCountDTO;
import cn.tedu.csmall.commons.restful.JsonResult;
import cn.tedu.csmall.commons.restful.ResponseCode;
import cn.tedu.csmall.stock.service.IStockService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/base/stock")
@Api(tags = "库存管理")
public class StockController {


    // 从Spring容器中获得具备发送远程请求的RestTemplate对象
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IStockService stockService;
    @PostMapping("/reduce/count")
    @ApiOperation("减少商品库存业务")
    // @SentinelResource标记的方法会被Sentinel监控
    // value的值是这个监控的名称,我们可以在"仪表台"中看到
    // blockHandler的值指定了请求被限流时运行的方法名称
    @SentinelResource(value = "减少库存方法(控制器)",blockHandler = "blockError",
                        fallback = "fallbackError")
    public JsonResult reduceCommodityCount(StockReduceCountDTO stockReduceCountDTO){
        // 生成随机出触发降级流程
        /*if(Math.random()<0.1){
            throw new
              CoolSharkServiceException(ResponseCode.INTERNAL_SERVER_ERROR,"异常");
        }*/
        // 这里发送RestTemplate请求
        // 调用cart模块的删除购物车的方法
        // 先确定要调用的路径
//        String url="http://localhost:20001/base/cart/delete" +
//                "?userId={1}&commodityCode={2}";
        // 再发起调用
        // getForObject参数1请求的路径   参数2返回类型的反射
        // 从参数3开始是url中{1} 占位符的值 参数4是{2}的值 以此类推
//        JsonResult jsonResult=restTemplate.getForObject(url,JsonResult.class,
//                "UU100",
//                stockReduceCountDTO.getCommodityCode());
//        System.out.println(jsonResult);
        stockService.reduceCommodityCount(stockReduceCountDTO);
        return JsonResult.ok("商品库存减少完成!");
    }
    // 这个方法是Sentinel注解中fallback属性指定的降级方法
    // 当前控制器方法运行发生异常时,Sentinel会运行下面的降级方法
    // 降级方法中,可以不直接结束请求,而去运行一些代替代码或者补救措施
    // 让用户获得最低限度的响应
    public JsonResult fallbackError(StockReduceCountDTO stockReduceCountDTO){
        return JsonResult.failed(ResponseCode.BAD_REQUEST,"因为运行异常,服务降级");
    }


    // Sentinel 限流方法应该满足如下要求
    // 1.必须是public修改
    // 2.返回值类型必须和控制方法一致(JsonResult)
    // 3.方法名称要和控制器方法限流注解中规定的名称一致(blockError)
    // 4.参数列表必须和控制器一致,可以在所以参数后声明BlockException来获得限流异常
    public JsonResult blockError(StockReduceCountDTO stockReduceCountDTO,
                                 BlockException e){
        return JsonResult.failed(ResponseCode.BAD_REQUEST,"服务器忙,请稍后再试");
    }





}






