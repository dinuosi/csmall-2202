package cn.tedu.csmall.stock.webapi.quartz;

import cn.tedu.csmall.stock.webapi.quartz.config.RabbitMQConfig;
import cn.tedu.csmall.stock.webapi.utils.RedisBloomUtils;
import org.checkerframework.checker.units.qual.A;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


public class QuartzJob implements Job {


    // RabbitTemplate就是amqp框架提供的发送消息的对象
    //@Autowired
    //private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisBloomUtils redisBloomUtils;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //输出当前时间
        System.out.println("--------------"+ LocalDateTime.now() +"---------------");
        // 先简单的发送一个字符串
        //rabbitTemplate.convertAndSend(RabbitMQConfig.STOCK_EX,
        //        RabbitMQConfig.STOCK_ROUT,"接收到减少库存的消息");
        //  首先确定要向布隆过滤器中保存的元素
        String[] colors={"red","origin","yellow","green","blue","pink","white"};
        //  使用RedisBloomUtils类将上面的数组元素保存在Redis(布隆过滤器)中
        redisBloomUtils.bfmadd("colorBloom",colors);
        // 下面就可以判断一个元素是否在这个布隆过滤器中了
        String elm="yellow";
        System.out.println("布隆过滤器判断"+elm+"是否存在:"+
                redisBloomUtils.bfexists("colorBloom",elm));




    }
}
