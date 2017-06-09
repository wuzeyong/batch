package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by WUZEYONG089 on 2017/5/3.
 */
@Setter
public abstract class AbstractCommExecutorWrapper<I,O> extends AbstractExecutorWrapper<I,O>{

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractExecutorWrapper.class);

    @Override
    protected O doExecute() {
        try {
            List<I> tasks = produceTasks();
            for(I task : tasks){
                if(LOGGER.isTraceEnabled()){
                    LOGGER.trace("Use Comm Mode:Unit[{}] in Thread[{}] consume task:{}",
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
            LOGGER.error("Thread[{}] of producers is Interrupted:{}", Thread.currentThread().getId(), e);
            //TODO 处理任务线程中断，需通知线程池重新启动线程
            return null;
        } catch (Exception e){
            ExecutorExceptionHandler.handleException(e);
            return null;
        }
    }

    protected abstract void handleFailedTask(I task);

}
