package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import com.wuzeyong.batch.namespace.entity.batch.TaskSet;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author WUZEYONG
 */
@Slf4j
public abstract class AbstractConsumerExecutorWrapper<I,O> extends AbstractExecutorWrapper<I,O>{

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
                if(log.isTraceEnabled()){
                    log.trace("Consumer Of Unit[{}] in Thread[{}] consume task from TaskPoolQueue:{}",
                            this.batchUnit.getClass().getSimpleName(),Thread.currentThread().getName(),task);
                }
                if(task!=null && !consumeTask(task)){
                    handleFailedTask(task);
                }
            }
            O result = checkResult();
            return result;
        } catch (InterruptedException e) {
            log.error("Thread[{}] of producers is Interrupted:{}", Thread.currentThread().getId(), e);
            //TODO 处理任务线程中断，需通知线程池重新启动线程
        } catch (Exception e){
            ExecutorExceptionHandler.handleException(e);
        }
        return null;
    }

    protected abstract void handleFailedTask(I task);

    @Override
    protected TaskSet<I> produceTask() throws Exception {
        throw new IllegalAccessException("Consumer don't need to produce tasks!");
    }

    @Override
    protected I decorateTask(TaskSet<I> taskSet) throws Exception {
        throw new IllegalAccessException("Consumer don't need to decorate task!");
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
