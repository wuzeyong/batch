package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.namespace.entity.batch.TaskSet;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.result.ProducerBaseResult;

/**
 * @author WUZEYONG
 */

public class ProducerExecutorWrapper extends AbstractProducerExecutorWrapper<BaseTask,BaseResult>{

    @Override
    protected TaskSet<BaseTask> produceTask() throws Exception {
        return this.batchUnit.produceTask();
    }

    @Override
    protected BaseTask decorateTask(TaskSet<BaseTask> taskSet) throws Exception {
        return this.batchUnit.decorateTask(taskSet);
    }

    @Override
    protected BaseResult checkResult() throws Exception {
        ProducerBaseResult producerBaseResult = new ProducerBaseResult();
        producerBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL);
        return producerBaseResult;
    }
}
