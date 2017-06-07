package com.wuzeyong.batch.wrapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wzy on 2017/5/29.
 */
public class ExecutorManager {

    protected static Map<Thread,String> producerStatus = new ConcurrentHashMap<Thread, String>();

    //线程状态
    //protected static ThreadLocal<Map<Thread,String>> threadsStatus = new ThreadLocal<Map<Thread, String>>();


    /**
     * 设置线程状态
     * @param thread 当前线程
     * @param status 线程状态
     */
    public static void setProducerStatus(Thread thread, String status){
        producerStatus.put(thread,status);
    }

    /**
     * 获取线程状态
     * @return
     */
    public static String getProducerStatus(Thread thread){
        return producerStatus.get(thread);
    }

    /**
     * 获取线程状态
     * @return
     */
    public static Map<Thread,String> getProducers(){
        return producerStatus;
    }

    //protected static <K,V> Map<K,V> getMap(ThreadLocal<Map<K,V>> threadLocal){
    //    Map<K,V> map = threadLocal.get();
    //    if(map == null){
    //        map = new HashMap<K, V>();
    //        threadLocal.set(map);
    //    }
    //    return map;
    //}

    public static void clear(){
        producerStatus.clear();
    }


}
