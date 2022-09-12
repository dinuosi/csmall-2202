package cn.tedu.csmall.business.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// 当前DemoAspect类的功能是为指定的方法进行aop实现
// 必须将当前切面设置类也交由Spring管理
@Component
// 表示当前类不是普通类,是做切面功能设计的
@Aspect
public class DemoAspect {

    // 1.定义切面
    //  @Pointcut是指定切面方法的注解
    //  注解中通过固定的格式指定或统配添加切面的方法
    @Pointcut("execution(public * cn.tedu.csmall.business.controller" +
            ".BusinessController.aopTest(..))")
    // 我们需要在注解下定义一个方法,代表这个切面的定义
    // 这个方法不需要任何内容,方法名足矣
    public void pointCut(){}

    // 2.织入内容
    //   向确定好的切面中添加需要运行的额外代码
    //   我们需要设计它的运行时机,这里以前置运行为例
    //   在注解中配置上面切面的方法名,pointCut()是上面方法名,带()是固定要求
    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        // 这个代码就会在aopTest方法运行之前运行
        System.out.println("前置advice运行");
        // JoinPoint可以声明在任何织入方法的参数中
        // JoinPoint会包含当前切面方法的各种信息,主要都是反射获取的
        // 最常用的就是当前切面方法的方法信息,例如方法名
        String methodName=joinPoint.getSignature().getName();
        System.out.println("切面方法为:"+methodName);
    }

    // 后置 Advice
    @After("pointCut()")
    public void after(){
        System.out.println("后置advice运行");
    }
    // 异常 Advice (只有切面的方法发生异常时才会运行)
    @AfterThrowing("pointCut()")
    public void throwing(){
        System.out.println("方法发生异常!");
    }

    // 环绕Advice
    @Around("pointCut()")
    // 环绕Advice要想正常执行,必须设置方法的返回值和参数
    // 它能够实现切面方法运行前后都添加代码
    // 参数类型必须是ProceedingJoinPoint,它是JoinPoint的子接口
    // 它拥有更多方法,其中包含针对环绕Advice调用方法返回值的功能
    // 环绕增强,参与到了原有方法代码的调用和返回值的接收工作
    // 所以环绕增强需要讲原有方法的返回值返回才能有保持原有的工作流程
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 这个方法运行时,当前切面的目标方法还没有执行
        System.out.println("环绕Advice前置执行");
        // 环绕增强调用目标方法,并接收返回值(只有环绕增强有这个步骤)
        Object obj=joinPoint.proceed();
        // 这里目标方法已经执行完毕
        System.out.println("环绕Advice后置执行");
        // 千万别忘了要返回obj
        return obj;
    }




}
