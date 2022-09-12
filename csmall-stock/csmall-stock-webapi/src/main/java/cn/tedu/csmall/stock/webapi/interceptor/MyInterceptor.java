package cn.tedu.csmall.stock.webapi.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Connection;

// Mybatis拦截器测试类
@Slf4j
// Mybatis拦截器配置声明用的注解
// 可以配置拦截多个jdbc中的对象
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class,Integer.class}
)})
public class MyInterceptor implements Interceptor {
    // Mybatis拦截器方法
    // invocation 就是要运行的目标(这里就是sql语句)
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("进入拦截器,准备拦截sql语句");
        // 从参数invocation中获得要运行的sql语句对象BoundSql
        BoundSql boundSql=((StatementHandler)invocation.getTarget())
                                        .getBoundSql();
        // 从boundSql中获取sql语句
        String sql=boundSql.getSql();
        log.info("要运行的原sql语句为:{}",sql);
        // 下面可以将sql语句进行更改
        sql=sql+" and 1=1";
        log.info("变更后的sql语句:{}",sql);
        // 利用反射强制赋值,将boundSql中的sql属性变化
        reflectUpdateSql(boundSql,"sql",sql);

        return invocation.proceed();
    }

    // 需要定义个方法,能够将sql语句进行改写
    // 但是sql语句已经在invocation我么需要利用反射,将其中的属性改写
    private void reflectUpdateSql(BoundSql boundSql,
                                  String attrName,String attrValue)
            throws NoSuchFieldException, IllegalAccessException {
        // 这个方法目标是将boundSql对象的sql强制赋值赋值
        // 反射的属性类
        Field field=boundSql.getClass().getDeclaredField(attrName);
        // 设置属性可强制赋值 设置之后就不是私有属性了
        field.setAccessible(true);
        // 将准备好的值赋值到这个属性中
        field.set(boundSql,attrValue);
    }
    // User 类   User类中有个私有属性password  没有getset方法
    // 反射是可以强制给password属性赋值的
    // BoundSql相当于User对象
    // attrName相当于password属性
    // attrValue相当于我们要强制付给属性的值

}
