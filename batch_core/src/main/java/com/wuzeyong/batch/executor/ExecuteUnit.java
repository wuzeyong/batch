package com.wuzeyong.batch.executor;

/**
 * 执行单元.
 * 
 * @param <I> 入参类型
 * @param <O> 出参类型
 *
 * @author WUZEYONG
 */
public interface ExecuteUnit<I, O> {
    
    /**
     * 执行任务.
     * 
     * @param input 输入待处理数据
     * @return 返回处理结果
     * @throws Exception 执行期异常
     */
    O execute(I input) throws Exception;
}
