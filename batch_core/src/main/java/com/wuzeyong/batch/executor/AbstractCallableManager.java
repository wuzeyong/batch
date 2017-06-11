package com.wuzeyong.batch.executor;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.wrapper.ExecutorWrapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author WUZEYONG
 */
public abstract class AbstractCallableManager<I,O> {

    protected final TaskExecutor taskExecutor;

    protected BlockingQueue<I> taskPoolQueue;

    protected List<Thread> producerThreads = new CopyOnWriteArrayList<Thread>();
    protected List<Thread> consumerThreads = new CopyOnWriteArrayList<Thread>();
    protected List<Thread> commThread = new CopyOnWriteArrayList<Thread>();


    public AbstractCallableManager(TaskExecutor taskExecutor) {
        this(taskExecutor,null);
    }

    public AbstractCallableManager(TaskExecutor taskExecutor,BlockingQueue<I> taskPoolQueue) {
        this.taskExecutor = taskExecutor;
        this.taskPoolQueue = taskPoolQueue;
    }

    protected O executeBatchInternal(ExecutorWrapper<I,O> executorWrapper,boolean isExceptionThrown, Map<String, Object> dataMap) {
        if(BatchCoreConstant.EXECUTOR_TYPE_PRODUCER.equals(executorWrapper.getExecutorType())){
            producerThreads.add(Thread.currentThread());
        }
        if(BatchCoreConstant.EXECUTOR_TYPE_CONSUMER.equals(executorWrapper.getExecutorType())){
            consumerThreads.add(Thread.currentThread());
        }
        if(BatchCoreConstant.EXECUTOR_TYPE_COMM.equals(executorWrapper.getExecutorType())){
            commThread.add(Thread.currentThread());
        }
        ExecutorExceptionHandler.setExceptionThrown(isExceptionThrown);
        ExecutorDataMap.setDataMap(dataMap);
        try{
            return executorWrapper.execute(taskPoolQueue);
        }catch (final Exception ex){
            ExecutorExceptionHandler.handleException(ex);
            return null;
        }
    }
}
