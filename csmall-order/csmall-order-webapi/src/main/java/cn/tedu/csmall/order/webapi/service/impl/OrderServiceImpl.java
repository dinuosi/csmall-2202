package cn.tedu.csmall.order.webapi.service.impl;

import cn.tedu.csmall.cart.service.ICartService;
import cn.tedu.csmall.commons.pojo.order.dto.OrderAddDTO;
import cn.tedu.csmall.commons.pojo.order.entity.Order;
import cn.tedu.csmall.commons.pojo.stock.dto.StockReduceCountDTO;
import cn.tedu.csmall.commons.restful.JsonPage;
import cn.tedu.csmall.order.service.IOrderService;
import cn.tedu.csmall.order.webapi.mapper.OrderMapper;
import cn.tedu.csmall.stock.service.IStockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 因为business模块要调用这个业务逻辑层中的方法
// 所以这个类中的方法也要注册到Nacos
@DubboService
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    // 当前order模块消费stock的业务逻辑层方法,以减少库存
    // 因为stock模块的减库存方法在Nacos中注册,所以可以使用Dubbo调用
    // 要想调用就必须使用@DubboReference,才能获得业务逻辑层实现类对象
    @DubboReference
    private IStockService dubboStockService;
    // order还需要cart模块的删除购物车商品的方法
    @DubboReference
    private ICartService dubboCartService;


    @Override
    public void orderAdd(OrderAddDTO orderAddDTO) {
        // 1.减少订单商品的库存(要调用stock模块的方法)
        StockReduceCountDTO stockReduceCountDTO=new StockReduceCountDTO();
        // 为减少库存的商品编号赋值
        stockReduceCountDTO.setCommodityCode(orderAddDTO.getCommodityCode());
        // 为减少库存的数量赋值
        stockReduceCountDTO.setReduceCount(orderAddDTO.getCount());
        dubboStockService.reduceCommodityCount(stockReduceCountDTO);
        // 2.删除订单中购物车商品的信息(要调用cart模块的方法)
        dubboCartService.deleteUserCart(orderAddDTO.getUserId(),
                                        orderAddDTO.getCommodityCode());
        // 3.新增订单
        // 实例化订单对象
        Order order=new Order();
        // 赋值同名属性
        BeanUtils.copyProperties(orderAddDTO,order);
        // 调用持久层方法
        orderMapper.insertOrder(order);
        log.info("新增订单完成:{}",order);
    }

    // 分页查询所有订单的方法
    // pageNum是要查询的页码
    // pageSize是每页的条数
    public JsonPage<Order> getAllOrdersByPage(Integer pageNum, Integer pageSize){

        // 利用PageHelper框架的功能,指定分页的查询的页码和每页条数
        // pageNum为1时,就是查询第一页,和SpringData的分页不同(SpringData分页0表示第一页)
        PageHelper.startPage(pageNum,pageSize);
        // 调用查询所有订单的方法
        // 因为上面设置了分页查询的条件,所以下面的查询就会自动在sql语句后添加limit关键字
        // 查询出的list就是需要查询的页码的数据
        List<Order> list=orderMapper.findAllOrders();
        // 我们完成了分页数据的查询,但是当前方法要求返回分页信息对象PageInfo
        // PageInfo中可以包含分页数据和各种分页信息,这些信息都是自定计算出来的
        // 要想获得这个对象,可以在执行分页查询后实例化PageInfo对象,所有分页信息会自动生成
        return JsonPage.restPage(new PageInfo<>(list));
    }










}
