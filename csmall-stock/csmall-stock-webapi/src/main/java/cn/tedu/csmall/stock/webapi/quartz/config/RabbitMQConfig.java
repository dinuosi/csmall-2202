package cn.tedu.csmall.stock.webapi.quartz.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// SpringBoot整合RabbitMQ之后
// 这些配置信息要保存在Spring容器中,所以这些配置也要交给SpringBoot管理
@Configuration
public class RabbitMQConfig {
    // 声明需要使用的交换机\路由Key\队列的名称
    public static final String STOCK_EX="stock_ex";
    public static final String STOCK_ROUT="stock_rout";
    public static final String STOCK_QUEUE="stock_queue";

    // 声明交换机,需要几个声明几个,这里就一个
    // 方法中实例化交换机对象,确定名称,保存到Spring容器
    @Bean
    public DirectExchange stockDirectExchange(){
        return new DirectExchange(STOCK_EX);
    }

    // 声明队列,需要几个声明几个,这里就一个
    // 方法中实例化队列对象,确定名称,保存到Spring容器
    @Bean
    public Queue stockQueue(){
        return new Queue(STOCK_QUEUE);
    }

    // 声明路由Key(交换机和队列的关系),需要几个声明几个,这里就一个
    // 方法中实例化路由Key对象,确定名称,保存到Spring容器
    @Bean
    public Binding stockBinding(){
        return BindingBuilder.bind(stockQueue()).to(stockDirectExchange())
                .with(STOCK_ROUT);
    }




}
