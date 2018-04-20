package com.elasticjob.demo.register;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.elasticjob.demo.job.AbstractSimpleTimeTaskJob;
import com.elasticjob.demo.spring.JobSchedulerForSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhou
 * @date 2018/4/20
 */
public class SimpleTimeTaskJobRegister implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTimeTaskJobRegister.class);

    private ApplicationContext applicationContext;

    private CoordinatorRegistryCenter coordinatorRegistryCenter;

    public void setRegistryCenter(CoordinatorRegistryCenter coordinatorRegistryCenter) {
        this.coordinatorRegistryCenter = coordinatorRegistryCenter;
    }

    public void init() {
        Map<String, AbstractSimpleTimeTaskJob> map = applicationContext.getBeansOfType(AbstractSimpleTimeTaskJob.class);
        for (String key : map.keySet()) {
            AbstractSimpleTimeTaskJob abstractSimpleTimeTaskJob = map.get(key);
            // 定时任务名称
            String jobName = abstractSimpleTimeTaskJob == null ? key : abstractSimpleTimeTaskJob.getJobName();
            // 执行时间表达式
            String cron = abstractSimpleTimeTaskJob.getCron();
            // 总分片数
            int shardingCount = abstractSimpleTimeTaskJob.getShardingTotalCount();

            if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(cron) || shardingCount == 0) {
                LOGGER.error("定时任务配【" + key + "】置错误！请检查相关配置！");
            } else {
                // 定义作业核心配置
                JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobName, cron, shardingCount).build();
                // 定义SIMPLE类型配置
                SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, abstractSimpleTimeTaskJob.getClass().getCanonicalName());
                // 定义Lite作业根配置
                LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).build();
                // 启动作业
                new JobSchedulerForSpring(abstractSimpleTimeTaskJob, coordinatorRegistryCenter, liteJobConfiguration, new BaseElasticJobListener());
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
