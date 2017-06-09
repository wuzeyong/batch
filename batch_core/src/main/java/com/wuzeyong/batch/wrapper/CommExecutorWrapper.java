package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.result.ProducerBaseResult;

import java.util.List;

/**
 * Created by WUZEYONG089 on 2017/5/3.
 */

public class CommExecutorWrapper extends AbstractCommExecutorWrapper<BaseTask,BaseResult>{

    @Override
    protected List<BaseTask> produceTasks() throws Exception {
        return this.batchUnit.produceTasks();
    }

    @Override
    protected boolean consumeTask(BaseTask task) throws Exception {
        return this.batchUnit.consumeTask(task);
    }

    @Override
    protected BaseResult checkResult() throws Exception {
        ProducerBaseResult producerBaseResult = new ProducerBaseResult();
        producerBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL);
        return producerBaseResult;
    }

    @Override
    protected void handleFailedTask(BaseTask task) {
        this.batchUnit.handleFailedTask(task);
    }
}
