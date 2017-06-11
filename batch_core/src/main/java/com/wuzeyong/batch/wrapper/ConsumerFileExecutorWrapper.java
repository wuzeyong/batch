package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.namespace.entity.batch.FileSet;
import com.wuzeyong.batch.result.BaseResult;

/**
 * @author WUZEYONG
 */
public class ConsumerFileExecutorWrapper extends AbstractConsumerExecutorWrapper<FileSet,BaseTask,BaseResult>{

    @Override
    protected boolean consumeTask(BaseTask task) throws Exception {
        return this.batchUnit.consumeTask(task);
    }

    @Override
    protected BaseResult checkResult() {
        return  this.batchUnit.checkConsumerResult();
    }

    @Override
    protected void handleFailedTask(BaseTask task) {
        this.batchUnit.handleFailedTask(task);
    }
}
