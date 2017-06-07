package com.wuzeyong.batch.namespace.entity.batch;

/**
 * Created by WUZEYONG089 on 2017/5/4.
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
