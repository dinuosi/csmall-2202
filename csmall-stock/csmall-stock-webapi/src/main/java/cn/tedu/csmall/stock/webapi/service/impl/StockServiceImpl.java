package cn.tedu.csmall.stock.webapi.service.impl;

import cn.tedu.csmall.commons.pojo.stock.dto.StockReduceCountDTO;

import cn.tedu.csmall.stock.service.IStockService;
import cn.tedu.csmall.stock.webapi.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;

// @DubboService注解表示当前业务逻辑层实现类中的所有方法
// 均会注册到Nacos,成为Dubbo可以发现的业务逻辑层方法
@DubboService
@Service
@Slf4j
public class StockServiceImpl implements IStockService {

    @Autowired
    private StockMapper stockMapper;


    @Override
    public void reduceCommodityCount(StockReduceCountDTO stockReduceCountDTO) {
        stockMapper.updateStockByCommodityCode(
                stockReduceCountDTO.getCommodityCode(), // 第一个参数是商品编号
                stockReduceCountDTO.getReduceCount());  // 第二个参数是减少的库存数
        log.info("库存减少完成!");

        String str="a";



    }
}







