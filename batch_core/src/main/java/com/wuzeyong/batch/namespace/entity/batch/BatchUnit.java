package com.wuzeyong.batch.namespace.entity.batch;



/**
 * @author WUZEYONG
 */
public interface BatchUnit<I,O> {

    public O checkResult() throws Exception;

    public TaskSet<I> produceTask() throws Exception;

    public I decorateTask(TaskSet<I> taskSet);

    public boolean consumeTask(I task) throws Exception;

    public void handleFailedTask(I task);
}
