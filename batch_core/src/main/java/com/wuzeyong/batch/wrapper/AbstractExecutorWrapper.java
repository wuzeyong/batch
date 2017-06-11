package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.namespace.entity.batch.BatchUnit;
import com.wuzeyong.batch.namespace.entity.batch.PageNode;
import com.wuzeyong.batch.namespace.entity.batch.TaskSet;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.concurrent.BlockingQueue;

/**
 * @author WUZEYONG
 */
@Slf4j
@Setter
public abstract class AbstractExecutorWrapper<I,O> implements ExecutorWrapper<I,O>{

    protected String executorType;

    protected DataSource dataSource;

    protected BatchUnit<I,O> batchUnit;

    protected PageNode pageNode;

    protected BlockingQueue<I> taskPoolQueue;

    protected abstract O doExecute() throws Exception;

    protected abstract TaskSet<I> produceTask() throws Exception;

    protected abstract I decorateTask(TaskSet<I> taskSet) throws Exception;

    protected abstract boolean consumeTask(I task) throws Exception;

    protected abstract O checkResult() throws Exception;

    @Override
    public O execute(BlockingQueue<I> taskPoolQueue) throws Exception{
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
