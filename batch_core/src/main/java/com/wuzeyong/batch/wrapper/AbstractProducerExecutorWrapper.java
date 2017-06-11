package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import com.wuzeyong.batch.namespace.entity.batch.TaskSet;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WUZEYONG
 */
@Slf4j
@Setter
public abstract class AbstractProducerExecutorWrapper<I,O> extends AbstractExecutorWrapper<I,O>{

    @Override
    protected O doExecute() {
        try {
            ExecutorManager.setProducerStatus(Thread.currentThread(), BatchCoreConstant.EXECUTOR_STATUS_START);
            TaskSet<I> taskSet = produceTask();
            while(taskSet.next()){
                I task = decorateTask(taskSet);
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
        } catch (InterruptedException e) {
            log.error("Thread[{}] of producers is Interrupted:{}", Thread.currentThread().getId(), e);
            //TODO 处理任务线程中断，需通知线程池重新启动线程
            return null;
        } catch (Exception e){
            ExecutorExceptionHandler.handleException(e);
            return null;
        }
    }

    @Override
    protected boolean consumeTask(I task) throws Exception{
        throw new IllegalAccessException("Producer don't need to consume task!");
    }
}
