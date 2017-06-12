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
package com.wuzeyong.batch.utils;

import com.wuzeyong.batch.namespace.entity.batch.BatchUnit;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 
 * @author WUZEYONG
 */
@Slf4j
public class AnnotationUtils {

    /**
     * 获取targetClazz类中，所有拥有annotationClazz注解的字段
     * @param annotationClazz
     * @param targetClazz
     * @return
     */
    public static Set<String> getFieldsByAnnotation(Class<? extends Annotation> annotationClazz, Class<?> targetClazz) {
        Set<String> result = new HashSet<String>();
        while (targetClazz != null) {
            Field[] fields = targetClazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(annotationClazz)) {
                    result.add(field.getName());
                }
            }
            targetClazz = targetClazz.getSuperclass();
        }
        return result;
    }

    /**
     * 获取所有拥有该annotationClazz注解的类，根据Order排序
     * @param annotationClazz
     * @return
     */
    public static List<BatchUnit> getBatchUnitByAnnotation(Class<? extends Annotation> annotationClazz) {
        List<BatchUnit> batchUnits = new ArrayList<BatchUnit>();
        Map<String,Object> instances = SpringContextUtils.getBeansWithAnnotation(annotationClazz);
        for (Map.Entry<String,Object> instance : instances.entrySet()){
            if(log.isDebugEnabled()){
                log.debug("annotationClazz:{}, key:{}", annotationClazz.toString(), instance.getKey());
                log.debug("annotationClazz:{}, value:{}",annotationClazz.toString(), instance.getValue().toString());
            }
            BatchUnit batchUnit = (BatchUnit)instance.getValue();
            batchUnits.add(batchUnit);
        }
        return batchUnits;
    }
}
