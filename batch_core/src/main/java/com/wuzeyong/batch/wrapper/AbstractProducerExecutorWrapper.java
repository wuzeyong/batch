package com.wuzeyong.batch.wrapper;


import com.google.common.base.Preconditions;
import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import com.wuzeyong.batch.result.ProducerBaseResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WUZEYONG
 */
@Slf4j
@Setter
public abstract class AbstractProducerExecutorWrapper<I,M,O> extends AbstractExecutorWrapper<I,M,O>{

    @Override
    protected O doExecute() {
        try {
            ExecutorManager.setProducerStatus(Thread.currentThread(), BatchCoreConstant.EXECUTOR_STATUS_START);
            I set = produceSet();
            Preconditions.checkNotNull(set);
            while(setHasNext(set)) {
                M task = decorateTask(set);
                boolean offerResult = taskPoolQueue.offer(task);
                while(!offerResult){
                    offerResult = taskPoolQueue.offer(task);
                    if(!offerResult) Thread.sleep(500);
                }
                if(log.isTraceEnabled()){
                    log.trace("Producer Of Unit[{}] in Thread[{}] put task to TaskPoolQueue:{}",
                            this.batchUnit.getClass().getSimpleName(),Thread.currentThread().getName(),task);
                }
            }
            O result = checkResult();
            ExecutorManager.setProducerStatus(Thread.currentThread(), BatchCoreConstant.EXECUTOR_STATUS_END);
            return result;
        } catch (Exception e){
            log.error("Producer Thread[{}] Occur Exception", Thread.currentThread().getId());
            //TODO 处理任务线程中断，需通知线程池重新启动线程
            ExecutorExceptionHandler.handleException(e);
            return returnFailedStatus();
        }
    }

    @Override
    protected boolean consumeTask(M task) throws Exception{
        throw new IllegalAccessException("Producer don't need to consume task!");
    }

    private O returnFailedStatus(){
        ProducerBaseResult producerBaseResult = new ProducerBaseResult();
        producerBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE);
        return (O)producerBaseResult;
    };
}
