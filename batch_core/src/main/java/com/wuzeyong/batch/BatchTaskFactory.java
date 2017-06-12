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
package com.wuzeyong.batch;

import com.wuzeyong.batch.namespace.entity.batch.BatchRule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Properties;

/**
 * 批量任务工厂.
 * 
 * @author WUZEYONG
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchTaskFactory {
    
    /**
     * 创建批量任务.
     * 
     * @param batchRule 任务
     * @return 批量任务
     */
    public static BatchTask createBatchTask(final BatchRule batchRule) {
        return new BatchTask(batchRule);
    }
    
    /**
     * 创建批量任务.
     * 
     * @param batchRule 任务
     * @param props 属性配置
     * @return 批量任务
     */
    public static BatchTask createBatchTask(final BatchRule batchRule, final Properties props) {
        return new BatchTask(batchRule, props);
    }
}
