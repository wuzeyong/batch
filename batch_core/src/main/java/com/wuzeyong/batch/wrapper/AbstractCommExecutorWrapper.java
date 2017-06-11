package com.wuzeyong.batch.wrapper;


import com.google.common.base.Preconditions;
import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.ExecutorExceptionHandler;
import com.wuzeyong.batch.result.CommBaseResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WUZEYONG
 */
@Slf4j
@Setter
public abstract class AbstractCommExecutorWrapper<I,M,O> extends AbstractExecutorWrapper<I,M,O>{

    @Override
    protected O doExecute() {
        try {
            I set = produceSet();
            Preconditions.checkNotNull(set);
            while(setHasNext(set)) {
                M task = decorateTask(set);
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
        } catch (Exception e){
            log.error("CommMode Thread[{}] Occur Exception", Thread.currentThread().getId());
            //TODO 处理任务线程中断，需通知线程池重新启动线程
            ExecutorExceptionHandler.handleException(e);
            return returnFailedStatus();
        }
    }

    protected abstract void handleFailedTask(M task);

    private O returnFailedStatus(){
        CommBaseResult commBaseResult = new CommBaseResult();
        commBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE);
        return (O)commBaseResult;
    };

}
