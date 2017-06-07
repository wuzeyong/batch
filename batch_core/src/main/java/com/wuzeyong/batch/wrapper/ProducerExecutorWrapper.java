package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.result.ProducerBaseResult;

import java.util.List;

/**
 * Created by WUZEYONG089 on 2017/5/3.
 */

public class ProducerExecutorWrapper extends AbstractProducerExecutorWrapper<BaseTask,BaseResult>{

    @Override
    protected List<BaseTask> produceTasks() throws Exception {
        return this.batchUnit.produceTasks();
    }

    @Override
    protected BaseResult checkResult() throws Exception {
        ProducerBaseResult producerBaseResult = new ProducerBaseResult();
        producerBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL);
        return producerBaseResult;
    }
}
