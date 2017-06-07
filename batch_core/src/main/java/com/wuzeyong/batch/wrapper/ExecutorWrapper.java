package com.wuzeyong.batch.wrapper;

import javax.sql.DataSource;
import java.util.concurrent.BlockingQueue;

/**
 * Created by WUZEYONG089 on 2017/5/3.
 */
public interface ExecutorWrapper<I,O> {

    public DataSource getDataSource();

    public String getExecutorType();

    public O execute(BlockingQueue<I> taskPoolQueue) throws Exception;


}
