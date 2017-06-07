package com.wuzeyong.batch.executor;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.wrapper.ConsumerExecutorWrapper;
import com.wuzeyong.batch.wrapper.ExecutorManager;
import com.wuzeyong.batch.wrapper.ProducerExecutorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;

/**
 * @author WUZEYONG089
 */
public final class BatchTaskExecutor implements TaskExecutor{

    private static Logger LOGGER = LoggerFactory.getLogger(BatchTaskExecutor.class);

    protected ExecutorEngine executorEngine;

    protected Collection<ProducerExecutorWrapper> producerExecutorWrappers;

    protected Collection<ConsumerExecutorWrapper> consumerExecutorWrappers;

    protected FutureTask<String> producersTask;

    protected FutureTask<String> consumersTask;

    protected ManageProducersCallable producersCallable;

    protected ManageConsumersCallable consumersCallable;

    protected Thread producersManagerThread;

    protected Thread consumersManagerThread;

    protected int queueSize = 100;

    protected BlockingQueue<BaseTask> taskPoolQueue;

    public  BatchTaskExecutor(ExecutorEngine executorEngine, Collection<ProducerExecutorWrapper> producerExecutorWrappers,
                              Collection<ConsumerExecutorWrapper> consumerExecutorWrappers) {
        this.executorEngine = executorEngine;
        this.producerExecutorWrappers = producerExecutorWrappers;
        this.consumerExecutorWrappers = consumerExecutorWrappers;
    }

    public void start(){
        if(taskPoolQueue == null){
            LOGGER.info("Create Task Pool Queue of size {}",queueSize);
            taskPoolQueue = new ArrayBlockingQueue<BaseTask>(queueSize);
        }
        if(producersCallable == null){
            producersCallable = new ManageProducersCallable(this,producerExecutorWrappers,taskPoolQueue);
        }
        if(consumersCallable == null){
            consumersCallable = new ManageConsumersCallable(this,consumerExecutorWrappers,taskPoolQueue);
        }
        startAsyncManageThread();
    }

    protected void startAsyncManageThread(){
        if(producersTask == null){
            producersTask = new FutureTask<String>(producersCallable);
            producersManagerThread = new Thread(producersTask);
        }
        producersManagerThread.start();

        if(consumersTask == null){
            consumersTask = new FutureTask<String>(consumersCallable);
            consumersManagerThread = new Thread(consumersTask);
        }
        consumersManagerThread.start();
    }

    protected void stopAsyncManageThread(){
        try {
            producersManagerThread.join();
        } catch (InterruptedException e) {
            LOGGER.error(" producers thread is interrupted",e);
        }
        try {
            consumersManagerThread.join();
        } catch (InterruptedException e) {
            LOGGER.error("consumers thread is interrupted", e);
        }
        producersTask = null;
        consumersTask = null;
        producersManagerThread = null;
        consumersManagerThread = null;
    }

    public synchronized void shutdown(){
        //TODO Runnable�ڲ����ƹر�
        stopAsyncManageThread();
        executorEngine.shutdown();
        ExecutorManager.clear();
        producersCallable = null;
        consumersCallable = null;
    }


    public String execute(){
        start();
        return getExecuteStatus();
    }

    private String getExecuteStatus() {
        String producersExecuteStatus = BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
        String consumersExecuteStatus = BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
        try{
            producersExecuteStatus = producersTask.get();
        }catch (Exception e){
            LOGGER.warn("Interrupted while waiting for the producers task return execute status",e);
        }
        try{
            consumersExecuteStatus = consumersTask.get();
        }catch (Exception e){
            LOGGER.warn("Interrupted while waiting for the consumers task return execute status",e);
        }
        if(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL.equals(producersExecuteStatus)
                && BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL.equals(consumersExecuteStatus)){
            return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
        }
        shutdown();
        return BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
    }


    @Override
    public ExecutorEngine getExecutorEngine() {
        return this.executorEngine;
    }
}
