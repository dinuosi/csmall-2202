package cn.tedu.csmall.cart.webapi.kafka;

import cn.tedu.csmall.commons.pojo.cart.entity.Cart;
import com.google.gson.Gson;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

// 我们需要周期性的向Kafka发送消息
// 需要将具备SpringBoot调度功能的类保存到Spring容器才行
@Component
public class Producer {

    // 能够实现将消息发送到Kafka的对象
    // 只要Kafka配置正确,这个对象会自动保存到Spring容器中,我们直接装配即可
    // KafkaTemplate<[话题名称的类型],[传递消息的类型]>
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    // 每隔10秒向Kafka发送信息
    int i=1;
    // fixedRate是周期运行,单位毫秒 10000ms就是1秒
    @Scheduled(fixedRate = 10000)
    // 这个方法只要启动SpringBoot项目就会按上面设置的时间运行
    public void sendMessage(){
        // 实例化一个Cart类型对象,用于发送消息
        Cart cart=new Cart();
        cart.setId(i++);
        cart.setCommodityCode("PC100");
        cart.setPrice(RandomUtils.nextInt(100)+200);
        cart.setCount(RandomUtils.nextInt(5)+1);
        cart.setUserId("UU100");
        // 将cart对象转换为json格式字符串
        Gson gson=new Gson();
        // 执行转换
        String json=gson.toJson(cart);
        System.out.println("本次发送的消息为:"+json);
        // 执行发送
        // send([话题名称],[发送的消息]),需要遵循上面kafkaTemplate声明的泛型类型
        kafkaTemplate.send("myCart",json);

    }




}
