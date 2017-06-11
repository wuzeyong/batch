package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.executor.BaseTask;
import com.wuzeyong.batch.namespace.entity.batch.FileSet;
import com.wuzeyong.batch.result.BaseResult;

/**
 * @author WUZEYONG
 */
public class ProducerFileExecutorWrapper extends AbstractProducerExecutorWrapper<FileSet,BaseTask,BaseResult>{

    @Override
    protected FileSet produceSet() throws Exception {
        return this.batchUnit.produceSet();
    }

    @Override
    protected boolean setHasNext(FileSet set) throws Exception {
        return set.next();
    }

    @Override
    protected BaseTask decorateTask(FileSet set) throws Exception {
        return this.batchUnit.decorateTask(set);
    }

    @Override
    protected BaseResult checkResult(){
        return  this.batchUnit.checkProducerResult();
    }
}
