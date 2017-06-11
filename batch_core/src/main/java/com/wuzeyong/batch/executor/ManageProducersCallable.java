package com.wuzeyong.batch.executor;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.wrapper.ProducerExecutorWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author WUZEYONG
 */
@Slf4j
public class ManageProducersCallable extends AbstractCallableManager<BaseTask,BaseResult> implements Callable<String>{

    protected Collection<ProducerExecutorWrapper> producerExecutorWrappers;

    public ManageProducersCallable(TaskExecutor taskExecutor, Collection<ProducerExecutorWrapper> producerExecutorWrappers,
                                   BlockingQueue<BaseTask> taskPoolQueue) {
        super(taskExecutor,taskPoolQueue);
        this.producerExecutorWrappers = producerExecutorWrappers;
    }

    @Override
    public String call() throws Exception{
        final boolean isExceptionThrown = ExecutorExceptionHandler.isExceptionThrown();
        final Map<String, Object> dataMap = ExecutorDataMap.getDataMap();
        taskExecutor.getExecutorEngine().execute(producerExecutorWrappers, new ExecuteUnit<ProducerExecutorWrapper, BaseResult>() {
            @Override
            public BaseResult execute(ProducerExecutorWrapper input) throws Exception {
                return executeBatchInternal(input, isExceptionThrown, dataMap);
            }
        }, new MergeUnit<BaseResult, String>() {
            @Override
            public String merge(List<BaseResult> params) {
                boolean flag = false;
                for(BaseResult baseResult : params){
                    String executeStatus = baseResult.getExecuteStatus();
                    if(!BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL.equals(executeStatus)){
                        flag = true;
                        break;
                    }
                }
                if(flag) return BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
                else return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
            }
        });
        log.info("[{}] Producer Threads Handle Tasks with PcMode!",producerThreads.size());
        producerThreads.clear();
        return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
    }
}
