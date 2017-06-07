package com.wuzeyong.batch.executor;

import com.wuzeyong.batch.exception.BatchException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 执行器执行时异常处理类.
 *
 * @author WUZEYONG089
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class ExecutorExceptionHandler {
    
    private static final ThreadLocal<Boolean> IS_EXCEPTION_THROWN = new ThreadLocal<Boolean>();
    
    /**
     * 设置是否将异常抛出.
     *
     * @param isExceptionThrown 是否将异常抛出
     */
    public static void setExceptionThrown(final boolean isExceptionThrown) {
        ExecutorExceptionHandler.IS_EXCEPTION_THROWN.set(isExceptionThrown);
    }
    
    /**
     * 获取是否将异常抛出.
     * 
     * @return 是否将异常抛出
     */
    public static boolean isExceptionThrown() {
        return null == IS_EXCEPTION_THROWN.get() ? true : IS_EXCEPTION_THROWN.get();
    }
    
    /**
     * 处理异常. 
     * 
     * @param ex 待处理的异常
     */
    public static void handleException(final Exception ex) {
        if (isExceptionThrown()) {
            throw new BatchException(ex);
        }
        log.error("exception occur: ", ex);
    }
}
