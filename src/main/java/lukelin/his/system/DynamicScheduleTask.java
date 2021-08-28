package lukelin.his.system;

import lukelin.his.service.AccountService;
import lukelin.his.service.YBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {
    @Autowired
    private Environment environment;

    @Autowired
    private AccountService feeService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        String autoRunEnabled = environment.getProperty("autoRun.enabled");
        if(!autoRunEnabled.equals("1"))
            return;

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> this.feeService.feeAutoRun(),

                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = environment.getProperty("autoRun.cron");
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
}
