package cn.tedu.csmall.stock.webapi.quartz;

import cn.tedu.csmall.commons.pojo.stock.dto.StockReduceCountDTO;
import cn.tedu.csmall.stock.service.IStockService;
import cn.tedu.csmall.stock.webapi.mapper.StockMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

// 从Spring容器中获得业务逻辑层对象,然后PC100商品新增库存数10
public class StockJob implements Job {

    @Autowired
    private IStockService stockService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        StockReduceCountDTO stockReduceCountDTO=new StockReduceCountDTO();
        stockReduceCountDTO.setCommodityCode("PC100");
        // 业务本身是减少库存,我们赋值减少库存数为-10,就是增加库存数10
        stockReduceCountDTO.setReduceCount(-10);
        stockService.reduceCommodityCount(stockReduceCountDTO);
    }
}
