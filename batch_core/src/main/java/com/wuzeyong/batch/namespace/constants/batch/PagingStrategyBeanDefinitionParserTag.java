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
package com.wuzeyong.batch.namespace.constants.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 解析标签.
 *
 * @author WUZEYONG
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PagingStrategyBeanDefinitionParserTag {
    
    public static final String ID_TAG = "id";
    
    public static final String PAGING_MODE_TAG = "paging-mode";

    public static final String TOTAL_PAGING_TAG = "total-paging";

    public static final String MACHINE_STRATEGIES_TAG = "machine-strategies";

    public static final String MACHINE_STRATEGY_TAG = "machine-strategy";

    public static final String SERVER_ID_TAG = "server-id";

    public static final String HANDLE_PAGING_TAG = "handle-paging";

    public static final String DATA_SOURCE_TAG = "data-source";




}
