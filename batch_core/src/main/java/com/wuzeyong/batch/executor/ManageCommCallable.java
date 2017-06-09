package com.wuzeyong.batch.executor;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.wrapper.CommExecutorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by WUZEYONG089 on 2017/5/19.
 */
public class ManageCommCallable extends AbstractCallableManager<BaseTask,BaseResult> implements Callable<String>{

    private static Logger LOGGER = LoggerFactory.getLogger(ManageCommCallable.class);


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
        LOGGER.info("[{}] Comm Threads Handle Tasks without PcMode!",commThread.size());
        commThread.clear();
        return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
    }
}
