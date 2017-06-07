package com.wuzeyong.batch.namespace.entity.batch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 配置项常量.
 * @author WUZEYONG089
 */
@RequiredArgsConstructor
@Getter
public enum ExtendPropertiesConstant {

    /**
     * 最小空闲工作线程数量.
     *
     * <p>
     * 默认值: 0
     * </p>
     */
    EXECUTOR_MIN_IDLE_SIZE("executor.min.idle.size", "0", int.class),

    /**
     * 最大工作线程数量.
     *
     * <p>
     * 默认值: 100
     * </p>
     */
    EXECUTOR_MAX_SIZE("executor.max.size", "100", int.class),

    /**
     * 工作线程空闲时超时时间.
     *
     * <p>
     * 单位: 毫秒.
     * 默认值: 60000毫秒.
     * </p>
     */
    EXECUTOR_MAX_IDLE_TIMEOUT_MILLISECONDS("executor.max.idle.timeout.millisecond", "60000", long.class);
    
    private final String key;
    
    private final String defaultValue;
    
    private final Class<?> type;
    
    /**
     * 根据属性键查找枚举.
     * 
     * @param key 属性键
     * @return 枚举值
     */
    public static ExtendPropertiesConstant findByKey(final String key) {
        for (ExtendPropertiesConstant each : ExtendPropertiesConstant.values()) {
            if (each.getKey().equals(key)) {
                return each;
            }
        }
        return null;
    }
}
