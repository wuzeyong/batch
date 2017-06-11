package com.wuzeyong.batch.namespace.entity.batch;

/**
 * @author WUZEYONG
 */
public interface FileSet {

    boolean next() throws Exception;

    String getStringFile(int index);

}
