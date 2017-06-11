package com.wuzeyong.batch.executor;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.wrapper.CommExecutorWrapper;
import com.wuzeyong.batch.wrapper.ConsumerExecutorWrapper;
import com.wuzeyong.batch.wrapper.ExecutorManager;
import com.wuzeyong.batch.wrapper.ProducerExecutorWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;

/**
 * @author WUZEYONG
 */
@Slf4j
public final class BatchTaskExecutor implements TaskExecutor{

    protected ExecutorEngine executorEngine;

    protected Collection<ProducerExecutorWrapper> producerExecutorWrappers;

    protected Collection<ConsumerExecutorWrapper> consumerExecutorWrappers;

    protected Collection<CommExecutorWrapper> commExecutorWrappers;

    protected FutureTask<String> producersTask;

    protected FutureTask<String> consumersTask;

    protected FutureTask<String> commTask;

    protected ManageProducersCallable producersCallable;

    protected ManageConsumersCallable consumersCallable;

    protected ManageCommCallable manageCommCallable;

    protected Thread producersManagerThread;

    protected Thread consumersManagerThread;

    protected Thread commManagerThread;

    protected int queueSize = 100;

    protected BlockingQueue<BaseTask> taskPoolQueue;

    protected String pcMode;

    public  BatchTaskExecutor(ExecutorEngine executorEngine, Collection<CommExecutorWrapper> commExecutorWrappers,
                              String pcMode) {
        this.executorEngine = executorEngine;
        this.commExecutorWrappers = commExecutorWrappers;
        this.pcMode = pcMode;
    }

    public  BatchTaskExecutor(ExecutorEngine executorEngine, Collection<ProducerExecutorWrapper> producerExecutorWrappers,
                              Collection<ConsumerExecutorWrapper> consumerExecutorWrappers,String pcMode) {
        this.executorEngine = executorEngine;
        this.producerExecutorWrappers = producerExecutorWrappers;
        this.consumerExecutorWrappers = consumerExecutorWrappers;
        this.pcMode = pcMode;
    }

    public void start(){
        if(BatchCoreConstant.Y.equals(pcMode)){
            if(taskPoolQueue == null){
                log.info("Create Task Pool Queue of size {}",queueSize);
                taskPoolQueue = new ArrayBlockingQueue<BaseTask>(queueSize);
            }
            if(producersCallable == null){
                producersCallable = new ManageProducersCallable(this,producerExecutorWrappers,taskPoolQueue);
            }
            if(consumersCallable == null){
                consumersCallable = new ManageConsumersCallable(this,consumerExecutorWrappers,taskPoolQueue);
            }
            startAsyncPCManageThread();
        }else {
            if(manageCommCallable == null){
                manageCommCallable = new ManageCommCallable(this,commExecutorWrappers);
            }
            startAsyncCommManageThread();
        }


    }

    protected void startAsyncPCManageThread(){
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

    protected void startAsyncCommManageThread(){
        if(commTask == null){
            commTask = new FutureTask<String>(manageCommCallable);
            commManagerThread = new Thread(commTask);
        }
        commManagerThread.start();
    }

    protected void stopAsyncManageThread(){
        try {
            producersManagerThread.join();
        } catch (InterruptedException e) {
            log.error(" producers thread is interrupted",e);
        }
        try {
            consumersManagerThread.join();
        } catch (InterruptedException e) {
            log.error("consumers thread is interrupted", e);
        }
        producersTask = null;
        consumersTask = null;
        producersManagerThread = null;
        consumersManagerThread = null;
    }

    public synchronized void shutdown(){
        //TODO Runnable内部控制关闭
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
        if(BatchCoreConstant.Y.equals(pcMode)){
            String producersExecuteStatus = BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
            String consumersExecuteStatus = BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
            try{
                producersExecuteStatus = producersTask.get();
            }catch (Exception e){
                log.warn("Interrupted while waiting for the producers task return execute status",e);
            }
            try{
                consumersExecuteStatus = consumersTask.get();
            }catch (Exception e){
                log.warn("Interrupted while waiting for the consumers task return execute status",e);
            }
            if(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL.equals(producersExecuteStatus)
                    && BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL.equals(consumersExecuteStatus)){
                return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
            }
        }else {
            String commExecuteStatus = BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
            try{
                commExecuteStatus = commTask.get();
            }catch (Exception e){
                log.warn("Interrupted while waiting for the comm task return execute status",e);

            }
            if(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL.equals(commExecuteStatus)){
                return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
            }

        }
        shutdown();
        return BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
    }


    @Override
    public ExecutorEngine getExecutorEngine() {
        return this.executorEngine;
    }
}
