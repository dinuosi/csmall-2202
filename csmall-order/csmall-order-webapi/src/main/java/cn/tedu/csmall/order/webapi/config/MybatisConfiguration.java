package cn.tedu.csmall.order.webapi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// Mybatis扫描必须指定到mapper包
@MapperScan("cn.tedu.csmall.order.webapi.mapper")
public class MybatisConfiguration {

}
