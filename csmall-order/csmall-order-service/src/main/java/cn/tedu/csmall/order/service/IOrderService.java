package cn.tedu.csmall.order.service;

import cn.tedu.csmall.commons.pojo.order.dto.OrderAddDTO;
import cn.tedu.csmall.commons.pojo.order.entity.Order;
import cn.tedu.csmall.commons.restful.JsonPage;


public interface IOrderService {

    // 新增订单到数据库的业务逻辑层方法
    void orderAdd(OrderAddDTO orderAddDTO);

    // 分页查询所有订单的方法
    JsonPage<Order> getAllOrdersByPage(Integer pageNum,Integer pageSize);


}
