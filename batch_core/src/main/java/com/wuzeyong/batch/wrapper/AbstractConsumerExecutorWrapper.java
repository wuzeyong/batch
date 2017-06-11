package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import com.wuzeyong.batch.result.ConsumerBaseResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author WUZEYONG
 */
@Slf4j
public abstract class AbstractConsumerExecutorWrapper<I,M,O> extends AbstractExecutorWrapper<I,M,O>{

    @Override
    protected O doExecute() {
        try {
            M task;
            while(true){
                task = this.taskPoolQueue.poll();
                //若当获取task为空且所有生产者都结束，则可以结束消费数据
                if(task == null && allProducersDone()){
                    break;
                }

                if(task == null){
                    task = this.taskPoolQueue.poll(1000, TimeUnit.MICROSECONDS);
                }

                if(task!= null && log.isTraceEnabled()){
                    log.trace("Consumer Of Unit[{}] in Thread[{}] consume task from TaskPoolQueue:{}",
                            this.batchUnit.getClass().getSimpleName(),Thread.currentThread().getName(),task);
                }

                //任务处理失败
                if(task!=null && !consumeTask(task)){
                    handleFailedTask(task);
                }
            }
            O result = checkResult();
            return result;
        }catch (Exception e){
            log.error("Consumer Thread[{}] Occur Exception", Thread.currentThread().getId());
            //TODO 处理任务线程中断，需通知线程池重新启动线程
            ExecutorExceptionHandler.handleException(e);
            return returnFailedStatus();
        }
    }

    protected abstract void handleFailedTask(M task);

    @Override
    protected I produceSet() throws Exception {
        throw new IllegalAccessException("Consumer don't need to produce tasks!");
    }

    @Override
    protected M decorateTask(I taskSet) throws Exception {
        throw new IllegalAccessException("Consumer don't need to decorate task!");
    }

    @Override
    protected boolean setHasNext(I set) throws Exception {
        throw new IllegalAccessException("Consumer don't need to check empty set!");
    }

    protected boolean allProducersDone(){
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

    private O returnFailedStatus(){
        ConsumerBaseResult consumerBaseResult = new ConsumerBaseResult();
        consumerBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE);
        return (O)consumerBaseResult;
    };
}
