package com.wuzeyong.batch;


import com.wuzeyong.batch.executor.ExecutorEngine;
import com.wuzeyong.batch.namespace.entity.batch.BatchRule;
import com.wuzeyong.batch.namespace.entity.batch.ExtendProperties;
import lombok.Getter;

import java.util.Properties;

/**
 * @author WUZEYONG089
 */
@Getter
public class BatchTask {

    private final ExtendProperties extendProperties;

    private final ExecutorEngine executorEngine;

    private final ExecuteContext executeContext;

    public BatchTask(final BatchRule batchRule) {
        this(batchRule,new Properties());
    }

    public BatchTask(final BatchRule batchRule, final Properties props) {
        extendProperties = new ExtendProperties(props);
        executorEngine = new ExecutorEngine(extendProperties);
        executeContext = new ExecuteContext(batchRule,executorEngine);
    }


    /**
     * �ر�����Դ,�ͷ������Դ.
     */
    public void shutdown() {
        executorEngine.shutdown();
    }



}
