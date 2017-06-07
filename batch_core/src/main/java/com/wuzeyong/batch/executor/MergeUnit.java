package com.wuzeyong.batch.executor;

import java.util.List;

/**
 * 合并执行单元.
 * 
 * @param <I> 入参类型
 * @param <O> 出参类型
 *
 * @author WUZEYONG089
 */
interface MergeUnit<I, O> {
    
    /**
     * 合并执行结果.
     * 
     * @param params 合并前数据 
     * @return 合并后结果
     */
    O merge(List<I> params);
}
