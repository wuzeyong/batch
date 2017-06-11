package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.result.ConsumerBaseResult;

/**
 * @author WUZEYONG
 */
public class ConsumerExecutorWrapper extends AbstractConsumerExecutorWrapper<BaseTask,BaseResult>{

    @Override
    protected boolean consumeTask(BaseTask task) throws Exception {
        return this.batchUnit.consumeTask(task);
    }

    @Override
    protected BaseResult checkResult() throws Exception {
        ConsumerBaseResult baseResult = new ConsumerBaseResult();
        baseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL);
        return  baseResult;
    }

    @Override
    protected void handleFailedTask(BaseTask task) {

    }
}
