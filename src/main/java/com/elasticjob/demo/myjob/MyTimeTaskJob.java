package com.elasticjob.demo.myjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.elasticjob.demo.job.AbstractSimpleTimeTaskJob;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhou on 2018/4/20.
 */
public class MyTimeTaskJob extends AbstractSimpleTimeTaskJob {

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(shardingContext.getJobParameter());
        System.out.println("MyTimeTaskJob总分片=" + shardingContext.getShardingTotalCount());
        System.out.println("MyTimeTaskJob当前分片=" + shardingContext.getShardingItem());
        System.out.println("MyTimeTaskJob第" + count.addAndGet(1) + "次执行，当前分片号为：" + shardingContext.getShardingParameter());
    }
}
