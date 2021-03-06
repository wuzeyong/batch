package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.result.BaseResult;

import java.sql.ResultSet;

/**
 * @author WUZEYONG
 */

public class ProducerExecutorWrapper extends AbstractProducerExecutorWrapper<ResultSet,BaseTask,BaseResult>{

    @Override
    protected ResultSet produceSet() throws Exception {
        return this.batchUnit.produceSet();
    }

    @Override
    protected boolean setHasNext(ResultSet set) throws Exception {
        return set.next();
    }

    @Override
    protected BaseTask decorateTask(ResultSet resultSet) throws Exception {
        return this.batchUnit.decorateTask(resultSet);
    }

    @Override
    protected BaseResult checkResult(){
        return  this.batchUnit.checkProducerResult();
    }
}
