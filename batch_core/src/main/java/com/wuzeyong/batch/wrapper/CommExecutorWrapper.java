package com.wuzeyong.batch.wrapper;

import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.result.BaseResult;

import java.sql.ResultSet;

/**
 * @author WUZEYONG
 */

public class CommExecutorWrapper extends AbstractCommExecutorWrapper<ResultSet,BaseTask,BaseResult>{


    @Override
    protected ResultSet produceSet() throws Exception {
        return this.batchUnit.produceSet();
    }

    @Override
    protected BaseTask decorateTask(ResultSet resultSet) throws Exception {
        return this.batchUnit.decorateTask(resultSet);
    }

    @Override
    protected boolean setHasNext(ResultSet set) throws Exception {
        return set.next();
    }

    @Override
    protected boolean consumeTask(BaseTask task) throws Exception {
        return this.batchUnit.consumeTask(task);
    }

    @Override
    protected BaseResult checkResult() {
        return  this.batchUnit.checkCommModeResult();

    }

    @Override
    protected void handleFailedTask(BaseTask task) {
        this.batchUnit.handleFailedTask(task);
    }
}
