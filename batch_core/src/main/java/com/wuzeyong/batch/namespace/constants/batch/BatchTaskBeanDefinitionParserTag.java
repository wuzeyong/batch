package com.wuzeyong.batch.namespace.constants.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 解析标签.
 *
 * @author WUZEYONG089
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchTaskBeanDefinitionParserTag {
    
    public static final String ID_TAG = "id";
    
    public static final String NAME_TAG = "name";

    public static final String DESCRIBE_TAG = "describe";

    public static final String ENABLE_TAG = "enable";

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
