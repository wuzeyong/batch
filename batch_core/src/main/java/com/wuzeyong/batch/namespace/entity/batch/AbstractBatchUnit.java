package com.wuzeyong.batch.namespace.entity.batch;


/**
 * @author WUZEYONG
 */
public abstract class AbstractBatchUnit<I,O> implements BatchUnit<I,O> {

    @Override
    public O checkResult() {
        return null;
    }

    @Override
    public void handleFailedTask(I task) {
        return;
    }


}
