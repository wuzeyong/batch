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
import com.wuzeyong.batch.wrapper.ExecutorWrapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author WUZEYONG
 */
public abstract class AbstractCallableManager {

    protected final TaskExecutor taskExecutor;

    protected BlockingQueue taskPoolQueue;

    protected List<Thread> producerThreads = new CopyOnWriteArrayList<Thread>();

    protected List<Thread> consumerThreads = new CopyOnWriteArrayList<Thread>();

    protected List<Thread> commThread = new CopyOnWriteArrayList<Thread>();


    public AbstractCallableManager(TaskExecutor taskExecutor) {
        this(taskExecutor,null);
    }

    public AbstractCallableManager(TaskExecutor taskExecutor,BlockingQueue taskPoolQueue) {
        this.taskExecutor = taskExecutor;
        this.taskPoolQueue = taskPoolQueue;
    }

    protected <I,M,O> O executeBatchInternal(ExecutorWrapper<I,M,O> executorWrapper,boolean isExceptionThrown, Map<String, Object> dataMap) {
        if(BatchCoreConstant.EXECUTOR_TYPE_PRODUCER.equals(executorWrapper.getExecutorType())){
            producerThreads.add(Thread.currentThread());
        }
        if(BatchCoreConstant.EXECUTOR_TYPE_CONSUMER.equals(executorWrapper.getExecutorType())){
            consumerThreads.add(Thread.currentThread());
        }
        if(BatchCoreConstant.EXECUTOR_TYPE_COMM.equals(executorWrapper.getExecutorType())){
            commThread.add(Thread.currentThread());
        }
        ExecutorExceptionHandler.setExceptionThrown(isExceptionThrown);
        ExecutorDataMap.setDataMap(dataMap);
        try{
            return executorWrapper.execute(taskPoolQueue);
        }catch (final Exception ex){
            ExecutorExceptionHandler.handleException(ex);
            return null;
        }
    }
}
