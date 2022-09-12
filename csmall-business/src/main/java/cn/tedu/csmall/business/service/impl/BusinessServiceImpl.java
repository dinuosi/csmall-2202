package cn.tedu.csmall.business.service.impl;

import cn.tedu.csmall.business.service.IBusinessService;
import cn.tedu.csmall.commons.exception.CoolSharkServiceException;
import cn.tedu.csmall.commons.pojo.order.dto.OrderAddDTO;
import cn.tedu.csmall.commons.restful.ResponseCode;
import cn.tedu.csmall.order.service.IOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessServiceImpl implements IBusinessService {

    // Dubbo在获取order模块的业务逻辑层实现类
    @DubboReference
    private IOrderService dubboOrderService;

    // 一旦编写这个注解@GlobalTransactional
    // seata就会将这个方法当做一个分布式事务的起点
    // 之后所有远程Dubbo调用的数据库操作要么都成功,要么都失败
    @GlobalTransactional
    @Override
    public void buy() {
        // 暂时模拟一个下单业务
        // 创建OrderAddDTO类,赋值并输出信息
        OrderAddDTO orderAddDTO=new OrderAddDTO();
        orderAddDTO.setCommodityCode("PC100");
        orderAddDTO.setUserId("UU100");
        orderAddDTO.setMoney(500);
        orderAddDTO.setCount(8);
        // 因为没有持久层,只能输出一下,表示运行正常
        log.info("新增订单信息为:{}",orderAddDTO);
        // dubbo调用生成订单方法
        dubboOrderService.orderAdd(orderAddDTO);
        // 为了验证我们seata是有效果的
        // 在当前业务逻辑层方法中随机发生异常
        // 我们可以通过观察正常运行时数据是否提交和发生异常是数据是否回滚来判断seata是否工作
        if(Math.random()<0.2){
            throw new CoolSharkServiceException(ResponseCode.INTERNAL_SERVER_ERROR,
                                            "发生随机异常");
        }

    }
}
