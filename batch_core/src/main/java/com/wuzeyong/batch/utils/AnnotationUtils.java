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
     * ��ȡtargetClazz���У�����ӵ��annotationClazzע����ֶ�
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
     * ��ȡ����ӵ�и�annotationClazzע����࣬����Order����
     * @param annotationClazz
     * @return
     */
    public static List<BatchUnit> getBatchUnitByAnnotation(Class<? extends Annotation> annotationClazz) {
        List<BatchUnit> batchUnits = new ArrayList<BatchUnit>();
        Map<String,Object> instances = SpringContextUtils.getClassInstance(annotationClazz);
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
