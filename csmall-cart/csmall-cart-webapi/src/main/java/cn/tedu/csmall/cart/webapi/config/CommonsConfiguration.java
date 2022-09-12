package cn.tedu.csmall.cart.webapi.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 当前项目默认情况下不会扫描commons项目中的资源和内容,编写这个类,配置扫描commons
@Configuration // 所有配置Spring的配置类必须添加这个注解
@ComponentScan(basePackages = "cn.tedu.csmall.commons.exception")
public class CommonsConfiguration {
}
