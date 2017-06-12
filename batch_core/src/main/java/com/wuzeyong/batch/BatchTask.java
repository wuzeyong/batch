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


import com.wuzeyong.batch.executor.ExecutorEngine;
import com.wuzeyong.batch.namespace.entity.batch.BatchRule;
import com.wuzeyong.batch.namespace.entity.batch.ExtendProperties;
import lombok.Getter;

import java.util.Properties;

/**
 * @author WUZEYONG
 */
@Getter
public class BatchTask {

    private final ExtendProperties extendProperties;

    private final ExecutorEngine executorEngine;

    private final ExecuteContext executeContext;

    public BatchTask(final BatchRule batchRule) {
        this(batchRule,new Properties());
    }

    public BatchTask(final BatchRule batchRule, final Properties props) {
        extendProperties = new ExtendProperties(props);
        executorEngine = new ExecutorEngine(extendProperties);
        executeContext = new ExecuteContext(batchRule,executorEngine);
    }


    /**
     * 关闭数据源,释放相关资源.
     */
    public void shutdown() {
        executorEngine.shutdown();
    }



}
