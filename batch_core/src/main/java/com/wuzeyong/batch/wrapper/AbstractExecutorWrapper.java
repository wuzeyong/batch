package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.namespace.entity.batch.BatchUnit;
import com.wuzeyong.batch.namespace.entity.batch.PageNode;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by WUZEYONG089 on 2017/5/3.
 */
@Setter
public abstract class AbstractExecutorWrapper<I,O> implements ExecutorWrapper<I,O>{

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractExecutorWrapper.class);

    protected String executorType;

    protected DataSource dataSource;

    protected BatchUnit<I,O> batchUnit;

    protected PageNode pageNode;

    protected BlockingQueue<I> taskPoolQueue;

    protected abstract O doExecute();

    protected abstract List<I> produceTasks() throws Exception;

    protected abstract boolean consumeTask(I task) throws Exception;

    protected abstract O checkResult() throws Exception;

    @Override
    public O execute(BlockingQueue<I> taskPoolQueue) {
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
