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
 * @author WUZEYONG089
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
