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
package com.wuzeyong.batch.namespace.entity.batch;

import com.google.common.base.Joiner;
import com.wuzeyong.batch.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

/**
 * 批量的配置属性.
 *
 * @author WUZEYONG
 */
public class ExtendProperties {

    private final Properties props;

    public ExtendProperties(final Properties props) {
        this.props = props;
        validate();
    }
    
    private void validate() {
        Set<String> propertyNames = props.stringPropertyNames();
        Collection<String> errorMessages = new ArrayList<String>(propertyNames.size());
        for (String each : propertyNames) {
            ExtendPropertiesConstant extextendPropertiesConstant = ExtendPropertiesConstant.findByKey(each);
            if (null == extextendPropertiesConstant) {
                continue;
            }
            Class<?> type = extextendPropertiesConstant.getType();
            String value = props.getProperty(each);
            if (type == boolean.class && !StringUtil.isBooleanValue(value)) {
                errorMessages.add(getErrorMessage(extextendPropertiesConstant, value));
                continue;
            }
            if (type == int.class && !StringUtil.isIntValue(value)) {
                errorMessages.add(getErrorMessage(extextendPropertiesConstant, value));
                continue;
            }
            if (type == long.class && !StringUtil.isLongValue(value)) {
                errorMessages.add(getErrorMessage(extextendPropertiesConstant, value));
            }
        }
        if (!errorMessages.isEmpty()) {
            throw new IllegalArgumentException(Joiner.on(" ").join(errorMessages));
        }
    }
    
    private String getErrorMessage(final ExtendPropertiesConstant extextendPropertiesConstant, final String invalidValue) {
        return String.format("Value '%s' of '%s' cannot convert to type '%s'.", invalidValue, extextendPropertiesConstant.getKey(), extextendPropertiesConstant.getType().getName());
    }
    
    /**
     * 获取配置项属性值.
     * 
     * @param extextendPropertiesConstant 配置项常量
     * @param <T> 返回值类型
     * @return 配置项属性值
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(final ExtendPropertiesConstant extextendPropertiesConstant) {
        String result = props.getProperty(extextendPropertiesConstant.getKey(), extextendPropertiesConstant.getDefaultValue());
        if (boolean.class == extextendPropertiesConstant.getType()) {
            return (T) Boolean.valueOf(result);
        }
        if (int.class == extextendPropertiesConstant.getType()) {
            return (T) Integer.valueOf(result);
        }
        if (long.class == extextendPropertiesConstant.getType()) {
            return (T) Long.valueOf(result);
        }
        return (T) result;
    }
}
