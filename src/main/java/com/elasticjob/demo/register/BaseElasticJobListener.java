package com.elasticjob.demo.register;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

/**
 *
 * @author zhou
 * @date 2018/4/20
 */
public class BaseElasticJobListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {

    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {

    }
}
