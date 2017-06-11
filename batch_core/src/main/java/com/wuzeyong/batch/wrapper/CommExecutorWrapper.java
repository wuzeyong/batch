package com.wuzeyong.batch.wrapper;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.namespace.entity.batch.TaskSet;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.result.CommBaseResult;

/**
 * @author WUZEYONG
 */

public class CommExecutorWrapper extends AbstractCommExecutorWrapper<BaseTask,BaseResult>{


    @Override
    protected TaskSet<BaseTask> produceTask() throws Exception {
        return this.batchUnit.produceTask();
    }

    @Override
    protected BaseTask decorateTask(TaskSet<BaseTask> taskSet) throws Exception {
        return this.batchUnit.decorateTask(taskSet);
    }

    @Override
    protected boolean consumeTask(BaseTask task) throws Exception {
        return this.batchUnit.consumeTask(task);
    }

    @Override
    protected BaseResult checkResult() throws Exception {
        CommBaseResult commBaseResult = new CommBaseResult();
        commBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL);
        return commBaseResult;
    }

    @Override
    protected void handleFailedTask(BaseTask task) {
        this.batchUnit.handleFailedTask(task);
    }
}
