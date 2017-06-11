package com.wuzeyong.batch.namespace.entity.batch;


/**
 * @author WUZEYONG
 */
public abstract class AbstractBatchUnit<I,M,O> implements BatchUnit<I,M,O> {

    @Override
    public O checkCommModeResult() {
        return null;
    }

    @Override
    public O checkProducerResult() {
        return null;
    }

    @Override
    public O checkConsumerResult() {
        return null;
    }

    @Override
    public void handleFailedTask(M task) {
        return;
    }


}
