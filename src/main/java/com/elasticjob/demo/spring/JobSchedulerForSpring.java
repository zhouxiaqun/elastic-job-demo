package com.elasticjob.demo.spring;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.event.JobEventBus;
import com.dangdang.ddframe.job.executor.AbstractElasticJobExecutor;
import com.dangdang.ddframe.job.executor.JobExecutorFactory;
import com.dangdang.ddframe.job.executor.JobFacade;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.schedule.LiteJob;
import com.dangdang.ddframe.job.lite.internal.schedule.LiteJobFacade;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

import java.util.Arrays;

/**
 *
 * @author zhou
 * @date 2018/4/20
 */
public class JobSchedulerForSpring {

    public static final String  ELASTIC_JOB_DATA_MAP_KEY = "elasticJob";

    private static final String JOB_FACADE_DATA_MAP_KEY  = "jobFacade";

    private final AbstractElasticJobExecutor jobExecutor;

    private final JobFacade jobFacade;


    public JobSchedulerForSpring(ElasticJob elasticJob, CoordinatorRegistryCenter coordinatorRegistryCenter, LiteJobConfiguration liteJobConfiguration, ElasticJobListener ... elasticJobListeners) {
        jobFacade = new LiteJobFacade(coordinatorRegistryCenter, liteJobConfiguration.getJobName(), Arrays.asList(elasticJobListeners), new JobEventBus());
        jobExecutor = JobExecutorFactory.getJobExecutor(elasticJob, jobFacade);
    }

    public void init(){
        JobDetail jobDetail = JobBuilder.newJob(LiteJob.class).withIdentity("").build();
    }
}
