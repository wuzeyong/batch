/*
 * Copyright 2017 The Batch Framework Author.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package com.wuzeyong.batch.executor;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.wrapper.ConsumerExecutorWrapper;
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
public class ManageConsumersCallable extends AbstractCallableManager implements Callable<String>{

    protected Collection<ConsumerExecutorWrapper> consumerExecutorWrappers;


    public ManageConsumersCallable(TaskExecutor taskExecutor, Collection<ConsumerExecutorWrapper> consumerExecutorWrappers,
                                   BlockingQueue<BaseTask> taskPoolQueue) {
        super(taskExecutor,taskPoolQueue);
        this.consumerExecutorWrappers = consumerExecutorWrappers;
    }

    @Override
    public String call() {
        final boolean isExceptionThrown = ExecutorExceptionHandler.isExceptionThrown();
        final Map<String, Object> dataMap = ExecutorDataMap.getDataMap();
        taskExecutor.getExecutorEngine().execute(consumerExecutorWrappers, new ExecuteUnit<ConsumerExecutorWrapper, BaseResult>() {
            @Override
            public BaseResult execute(ConsumerExecutorWrapper input) throws Exception {
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
        log.info("[{}] Conusumer Threads Handle Tasks with PcMode!",consumerThreads.size());
        consumerThreads.clear();
        return BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL;
    }
}
