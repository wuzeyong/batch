package com.wuzeyong.batch.utils;

import com.wuzeyong.batch.namespace.entity.batch.BatchUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 
 * Created by WUZEYONG089 on 2016/9/01.
 */
public class AnnotationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationUtils.class);

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
        Map<String,Object> instances = SpringContextUtils.getClassInstance(annotationClazz);
        for (Map.Entry<String,Object> instance : instances.entrySet()){
            LOGGER.info("annotationClazz:{}, key:{}",annotationClazz.toString(), instance.getKey());
            LOGGER.info("annotationClazz:{}, value:{}",annotationClazz.toString(), instance.getValue().toString());
            BatchUnit batchUnit = (BatchUnit)instance.getValue();
            batchUnits.add(batchUnit);
        }
        return batchUnits;
    }
}
