package com.wuzeyong.batch.wrapper;


import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by WUZEYONG089 on 2017/5/3.
 */
@Setter
public abstract class AbstractProducerExecutorWrapper<I,O> extends AbstractExecutorWrapper<I,O>{

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractExecutorWrapper.class);

    @Override
    protected O doExecute() {
        try {
            ExecutorManager.setProducerStatus(Thread.currentThread(), BatchCoreConstant.EXECUTOR_STATUS_START);
            List<I> tasks = produceTasks();
            for(I task : tasks){
                //当队列满时，自旋等待
                boolean offerResult = taskPoolQueue.offer(task);
                while(!offerResult){
                    offerResult = taskPoolQueue.offer(task);
                    if(!offerResult) Thread.sleep(500);
                }
                if(LOGGER.isTraceEnabled()){
                    LOGGER.trace("Producer Of Unit[{}] in Thread[{}] put task to TaskPoolQueue:{}",
                            this.batchUnit.getClass().getSimpleName(),Thread.currentThread().getName(),task);
                }
            }
            O result = checkResult();
            ExecutorManager.setProducerStatus(Thread.currentThread(), BatchCoreConstant.EXECUTOR_STATUS_END);
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

    @Override
    protected boolean consumeTask(I task) throws Exception{
        throw new IllegalAccessException("Producer don't need to consume task!");
    }
}
