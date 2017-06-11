package com.wuzeyong.batch;

import com.wuzeyong.batch.namespace.entity.batch.BatchRule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Properties;

/**
 * 批量任务工厂.
 * 
 * @author WUZEYONG
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchTaskFactory {
    
    /**
     * 创建批量任务.
     * 
     * @param batchRule 任务
     * @return 批量任务
     */
    public static BatchTask createBatchTask(final BatchRule batchRule) {
        return new BatchTask(batchRule);
    }
    
    /**
     * 创建批量任务.
     * 
     * @param batchRule 任务
     * @param props 属性配置
     * @return 批量任务
     */
    public static BatchTask createBatchTask(final BatchRule batchRule, final Properties props) {
        return new BatchTask(batchRule, props);
    }
}
