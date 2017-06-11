package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import com.wuzeyong.batch.namespace.entity.batch.TaskSet;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WUZEYONG
 */
@Slf4j
@Setter
public abstract class AbstractCommExecutorWrapper<I,O> extends AbstractExecutorWrapper<I,O>{

    @Override
    protected O doExecute() {
        try {
            TaskSet<I> taskSet = produceTask();
            while(taskSet.next()){
                I task = decorateTask(taskSet);
                if(log.isTraceEnabled()){
                    log.trace("Use Comm Mode:Unit[{}] in Thread[{}] consume task:{}",
                            this.batchUnit.getClass().getSimpleName(),Thread.currentThread().getName(),task);
                }
                boolean result = consumeTask(task);
                if(!result){
                    handleFailedTask(task);
                }
            }
            O result = checkResult();
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

    protected abstract void handleFailedTask(I task);

}
