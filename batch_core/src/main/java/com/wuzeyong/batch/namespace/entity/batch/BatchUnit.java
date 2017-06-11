package com.wuzeyong.batch.namespace.entity.batch;


/**
 * @author WUZEYONG
 */
public interface BatchUnit<I,M,O> {

    public I produceSet() throws Exception;

    public M decorateTask(I set) throws Exception;

    public boolean consumeTask(M task) throws Exception;

    public O checkCommModeResult();

    public O checkProducerResult();

    public O checkConsumerResult();

    public void handleFailedTask(M task);
}
