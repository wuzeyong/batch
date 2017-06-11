package com.wuzeyong.batch.wrapper;

import javax.sql.DataSource;
import java.util.concurrent.BlockingQueue;

/**
 * @author WUZEYONG
 */
public interface ExecutorWrapper<I,M,O> {

    public DataSource getDataSource();

    public String getExecutorType();

    public O execute(BlockingQueue taskPoolQueue) throws Exception;


}
