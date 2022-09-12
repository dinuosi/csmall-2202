package cn.tedu.csmall.stock.webapi.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 这个配置类就是在配置已经保存在Spring容器中的调度器Scheduler
// 我们需要按下面格式进行配置,才能让Scheduler正常工作
@Configuration
public class QuartzConfig {
    // 创建一个JobDetail(工作详情)类对象,保存到Spring容器中,这个类用于封装我们编写的job接口实现类
    // @Bean注解标记的方法的返回值会自动保存到Spring容器中(方法名随意)
    @Bean
    public JobDetail showTime(){
        System.out.println("showTime方法运行");
        return JobBuilder.newJob(QuartzJob.class)   // 绑定要运行的任务类的反射
                .withIdentity("date")               // 设置这个job的名称
                .storeDurably()                     // 信息持久
                // 设置storeDurably之后,当没有触发器指向这个JobDetail时,JobDetail也不会从
                // Spring容器中删除,如果不设置这行,就会自动从Spring容器中删除
                .build();
    }

    // 下面要声明触发器,Trigger,触发器决定我们的工作\任务何时触发
    @Bean
    public Trigger showTimeTrigger(){
        System.out.println("showTime触发器运行");
        // 定义Cron表达式   每分钟触发一次的定义
        CronScheduleBuilder cronScheduleBuilder=
                CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(showTime())        // 绑定JobDetail JobDetail对象已经在Spring容器中
                .withIdentity("dateTrigger")       // 定义触发器名称
                .withSchedule(cronScheduleBuilder) // 绑定Cron表达式
                .build();

    }

    @Bean
    public JobDetail addStock(){
        return JobBuilder.newJob(StockJob.class)
                .withIdentity("addStock")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger addStockTrigger(){
        CronScheduleBuilder cronScheduleBuilder=
                CronScheduleBuilder.cronSchedule("0 0/2 * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(addStock())
                .withIdentity("addStockTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }






}
