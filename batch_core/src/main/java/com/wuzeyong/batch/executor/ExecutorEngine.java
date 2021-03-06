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


import com.google.common.util.concurrent.*;
import com.wuzeyong.batch.exception.BatchException;
import com.wuzeyong.batch.namespace.entity.batch.ExtendProperties;
import com.wuzeyong.batch.namespace.entity.batch.ExtendPropertiesConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 多线程执行引擎
 *
 * @author WUZEYONG
 */
@Slf4j
public final class ExecutorEngine {
    
    private final ListeningExecutorService executorService;
    
    public ExecutorEngine(final ExtendProperties extendProperties) {
        int executorMinIdleSize = (Integer)extendProperties.getValue(ExtendPropertiesConstant.EXECUTOR_MIN_IDLE_SIZE);
        int executorMaxSize = (Integer)extendProperties.getValue(ExtendPropertiesConstant.EXECUTOR_MAX_SIZE);
        long executorMaxIdleTimeoutMilliseconds = (Long)extendProperties.getValue(ExtendPropertiesConstant.EXECUTOR_MAX_IDLE_TIMEOUT_MILLISECONDS);
        executorService = MoreExecutors.listeningDecorator(MoreExecutors.getExitingExecutorService(
                new ThreadPoolExecutor(executorMinIdleSize, executorMaxSize, executorMaxIdleTimeoutMilliseconds, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>())));
    }
    
    /**
     * 多线程执行任务.
     * 
     * @param inputs 输入参数
     * @param executeUnit 执行单元
     * @param <I> 入参类型
     * @param <O> 出参类型
     * @return 执行结果
     */
    public <I, O> List<O> execute(final Collection<I> inputs, final ExecuteUnit<I, O> executeUnit) {
        ListenableFuture<List<O>> futures = submitFutures(inputs, executeUnit);
        addCallback(futures);
        return getFutureResults(futures);
    }
    
    /**
     * 多线程执行任务并归并结果.
     * 
     * @param inputs 执行入参
     * @param executeUnit 执行单元
     * @param mergeUnit 合并结果单元
     * @param <I> 入参类型
     * @param <M> 中间结果类型
     * @param <O> 最终结果类型
     * @return 执行结果
     */
    public <I, M, O> O execute(final Collection<I> inputs, final ExecuteUnit<I, M> executeUnit, final MergeUnit<M, O> mergeUnit) {
        return mergeUnit.merge(execute(inputs, executeUnit));
    }
    
    /**
     * 安全关闭执行器,并释放线程.
     */
    public void shutdown() {
        executorService.shutdownNow();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (final InterruptedException ignored) {
        }
        if (!executorService.isTerminated()) {
            throw new BatchException("ExecutorEngine can not been terminated");
        }
    }
    
    private <I, O> ListenableFuture<List<O>> submitFutures(final Collection<I> inputs, final ExecuteUnit<I, O> executeUnit) {
        Set<ListenableFuture<O>> result = new HashSet<ListenableFuture<O>>(inputs.size());
        for (final I each : inputs) {
            result.add(executorService.submit(new Callable<O>() {
                
                @Override
                public O call() throws Exception {
                    return executeUnit.execute(each);
                }
            }));
        }
        return Futures.allAsList(result);
    }
    
    private <T> void addCallback(final ListenableFuture<T> allFutures) {
        Futures.addCallback(allFutures, new FutureCallback<T>() {
            @Override
            public void onSuccess(final T result) {
                log.trace("Concurrent execute result success {}", result);
            }

            @Override
            public void onFailure(final Throwable thrown) {
                log.error("Concurrent execute result error {}", thrown);
            }
        });
    }
    
    private <O> O getFutureResults(final ListenableFuture<O> futures) {
        try {
            return futures.get();
        } catch (final InterruptedException   ex) {
            ExecutorExceptionHandler.handleException(ex);
            return null;
        } catch (final  ExecutionException ex) {
            ExecutorExceptionHandler.handleException(ex);
            return null;
        }

    }
}
