package cn.tedu.csmall.stock.webapi.config;

import cn.tedu.csmall.stock.webapi.interceptor.MyInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

//这个类是配置Mybatis拦截器生效的配置类
@Configuration
// 配置Mybatis拦截器生效的固定代码
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class InterceptorConfig {
    // 获得Mybatis的会话管理器
    // Mybatis会话管理器就是执行连接操作数据库的核心类
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    // 下面方法是将Mybatis会话管理器中所有连接和我们编写的拦截器关联,使拦截器生效
    @PostConstruct
    public void addInterceptors(){
        // 实例化我们编写的拦截器
        Interceptor interceptor=new MyInterceptor();
        for (SqlSessionFactory factory:sqlSessionFactoryList){
            factory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
