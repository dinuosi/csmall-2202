package cn.tedu.csmall.cart.webapi.kafka;

import cn.tedu.csmall.commons.pojo.cart.entity.Cart;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// 因为Kafka接收消息是自动的,所以这个类也必须交由Spring容器管理0
@Component
public class Consumer {

    // SpringKafka框架接收Kafka中的消息使用监听机制
    // SpringKafka框架提供一个监听器,专门负责关注指定的话题名称
    // 只要该话题名称中有消息,会自动获取该消息,并调用下面方法
    @KafkaListener(topics = "myCart")
    // 上面注解和下面方法关联,方法的参数就是接收到的消息
    public void received(ConsumerRecord<String,String> record){
        // 方法参数类型必须是ConsumerRecord
        // ConsumerRecord<[话题名称类型],[消息类型]>
        // 获取消息内容
        String json=record.value();
        // 要想在java中使用,需要转换为java对象
        Gson gson=new Gson();
        // 将json转换为java对象,需要提供转换目标类型的反射
        Cart cart=gson.fromJson(json,Cart.class);
        System.out.println("接收到对象为:"+cart);
    }




}
