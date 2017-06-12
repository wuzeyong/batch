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
public final class BatchTaskBeanDefinitionParserTag {
    
    public static final String ID_TAG = "id";
    
    public static final String NAME_TAG = "name";

    public static final String DESCRIBE_TAG = "describe";

    public static final String ENABLE_TAG = "enable";

    public static final String PRODUCER_CONSUMER_TAG = "pcMode";

    public static final String DATASOURCE_TAG = "dataSource";

    public static final String TARGETS_TAG = "targets";

    public static final String TARGET_TAG = "target";

    public static final String PROPERTIES_TAG = "props";

    public static final String UNITS_TAG = "units";

    public static final String UNIT_TAG = "unit";

    public static final String PAGING_STRATEGY_TAG = "paging-strategy";

    public static final String ROUTES_TAG = "routes";

    public static final String ROUTE_TAG = "route";

    public static final String ALLOW_MANUAL_EXECUTE_TAG = "allowManualExecute";

    public static final String EXECUTE_CLASS_TAG = "executeClass";

    public static final String CURRENT_UNIT_TAG = "currentUnit";

    public static final String EXECUTE_STATUS_TAG = "executeStatus";

    public static final String NEXT_UNIT_TAG = "nextUnit";



}
