package com.wuzeyong.batch;


import com.wuzeyong.batch.namespace.entity.config.TaskConfig;

import java.util.Properties;

/**
 * 基于Spring命名空间的批量任务
 * @author WUZEYONG
 */
public class SpringBatchTask extends BatchTask{

    public SpringBatchTask(final TaskConfig taskConfig,final Properties props) {
        super(new BatchRuleBuilder(taskConfig).build(), props);
    }
}
