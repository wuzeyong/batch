package com.wuzeyong.batch.namespace.entity.batch;


import java.util.List;

/**
 * Created by WUZEYONG089 on 2017/5/4.
 */
public interface BatchUnit<I,O> {

    public O checkResult() throws Exception;

    public List<I> produceTasks() throws Exception;

    public boolean consumeTask(I task) throws Exception;

    public void handleFailedTask(I task);
}
