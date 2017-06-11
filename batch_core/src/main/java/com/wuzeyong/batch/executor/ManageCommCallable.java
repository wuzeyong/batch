package com.wuzeyong.batch.executor;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.wrapper.CommExecutorWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author WUZEYONG
 */
@Slf4j
public class ManageCommCallable extends AbstractCallableManager implements Callable<String>{

    protected Collection<CommExecutorWrapper> commExecutorWrappers;

    public ManageCommCallable(TaskExecutor taskExecutor, Collection<CommExecutorWrapper> commExecutorWrappers) {
        super(taskExecutor,null);
        this.commExecutorWrappers = commExecutorWrappers;
    }

    @Override
    public String call() {
        final boolean isExceptionThrown = ExecutorExceptionHandler.isExceptionThrown();
        final Map<String, Object> dataMap = ExecutorDataMap.getDataMap();
         taskExecutor.getExecutorEngine().execute(commExecutorWrappers, new ExecuteUnit<CommExecutorWrapper, BaseResult>() {
            @Override
            public BaseResult execute(CommExecutorWrapper input) throws Exception {
                return executeBatchInternal(input,isExceptionThrown, dataMap);
            }
        }, new MergeUnit<BaseResult, String>() {
            @Override
            public String merge(List<BaseResult> params) {
                boolean flag = false;
                for (BaseResult baseResult : params) {
                    String executeStatus = baseResult.getExecuteStatus();
                    if (!BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL.equals(executeStatus)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) return BatchCoreConstant.EXECUTE_STATUS_UNEXECUTE;
                else return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
            }
        });
        log.info("[{}] Comm Threads Handle Tasks without PcMode!",commThread.size());
        commThread.clear();
        return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
    }
}
