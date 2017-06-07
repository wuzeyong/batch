package com.wuzeyong.batch;


import com.wuzeyong.batch.executor.ExecutorEngine;
import com.wuzeyong.batch.namespace.entity.batch.BatchRule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author WUZEYONG089
 */
@RequiredArgsConstructor
@Getter
public final class ExecuteContext {
    
    private final BatchRule batchRule;

    private final ExecutorEngine executorEngine;
}
