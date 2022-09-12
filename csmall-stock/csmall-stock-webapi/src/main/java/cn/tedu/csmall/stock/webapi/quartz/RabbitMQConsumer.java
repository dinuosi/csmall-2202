package cn.tedu.csmall.stock.webapi.quartz;

import cn.tedu.csmall.stock.webapi.quartz.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 这个对象也是需要交由Spring容器管理的,才能实现监听Spring容器中保存的队列的效果
@Component
// 和Kafka不同的是Kafka在一个方法上声明监听器
// 而RabbitMQ是在类上声明,监听具体的队列名称
@RabbitListener(queues = {RabbitMQConfig.STOCK_QUEUE})
public class RabbitMQConsumer {

    // 监听了类,但是运行代码的一定是个方法
    // 框架要求这个类中只允许一个方法包含下面这个注解
    // 表示这个方法是处理消息的方法
    // 方法的参数就是消息的值
    @RabbitHandler
    public void process(String str){
        System.out.println("接收到的消息为:"+str);
    }


}
