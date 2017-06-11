package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.namespace.entity.batch.BatchUnit;
import com.wuzeyong.batch.namespace.entity.batch.PageNode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.concurrent.BlockingQueue;

/**
 * @author WUZEYONG
 */
@Slf4j
@Setter
public abstract class AbstractExecutorWrapper<I,M,O> implements ExecutorWrapper<I,M,O>{

    protected String executorType;

    protected DataSource dataSource;

    protected BatchUnit<I,M,O> batchUnit;

    protected PageNode pageNode;

    protected BlockingQueue<M> taskPoolQueue;

    protected abstract O doExecute() throws Exception;

    protected abstract I produceSet() throws Exception;

    protected abstract M decorateTask(I set) throws Exception;

    protected abstract boolean setHasNext(I set) throws Exception;

    protected abstract boolean consumeTask(M task) throws Exception;

    protected abstract O checkResult();

    @Override
    public O execute(BlockingQueue taskPoolQueue) throws Exception{
        this.taskPoolQueue = taskPoolQueue;
        return doExecute();
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public String getExecutorType() {
        return this.executorType;
    }
}
