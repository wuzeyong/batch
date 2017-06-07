package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by WUZEYONG089 on 2017/5/3.
 */
public abstract class AbstractConsumerExecutorWrapper<I,O> extends AbstractExecutorWrapper<I,O>{

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractConsumerExecutorWrapper.class);

    @Override
    protected O doExecute() {
        try {
            I task;
            while(true){
                task = this.taskPoolQueue.poll();
                //若当获取task为空且所有生产者都结束，则可以结束消费数据
                if(task == null && allProducerThreadsIsOver() ){
                    break;
                }
                if(task == null){
                    task = this.taskPoolQueue.poll(1000, TimeUnit.MICROSECONDS);
                }
                //任务处理失败
                if(LOGGER.isTraceEnabled()){
                    LOGGER.trace("Consumer Of Unit[{}] in Thread[{}] consume task from TaskPoolQueue:{}",
                            this.batchUnit.getClass().getSimpleName(),Thread.currentThread().getName(),task);
                }
                if(task!=null && !consumeTask(task)){
                    handleFailedTask(task);
                }
            }
            O result = checkResult();
            return result;
        } catch (InterruptedException e) {
            LOGGER.error("Thread[{}] of producers is Interrupted:{}", Thread.currentThread().getId(), e);
            //TODO 处理任务线程中断，需通知线程池重新启动线程
        } catch (Exception e){
            ExecutorExceptionHandler.handleException(e);
        }
        return null;
    }

    protected abstract void handleFailedTask(I task);

    @Override
    protected List<I> produceTasks() throws Exception {
        throw new IllegalAccessException("Consumer don't need to produce tasks!");
    }

    protected boolean allProducerThreadsIsOver(){
        Map<Thread,String> producers = ExecutorManager.getProducers();
        if(producers.size() == 0){
            return false;
        }
        for(Map.Entry<Thread,String> producer : producers.entrySet()){
            if(!BatchCoreConstant.EXECUTOR_STATUS_END.equals(producer.getValue())){
                return false;
            }
        }
        return true;
    }
}
