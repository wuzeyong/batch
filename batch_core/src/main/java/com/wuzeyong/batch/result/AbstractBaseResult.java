package com.wuzeyong.batch.result;

import lombok.Setter;

/**
 * Created by WUZEYONG089 on 2017/5/3.
 */
public abstract class AbstractBaseResult implements BaseResult {

    @Setter
    private String executeStatus;

    @Override
    public String getExecuteStatus() {
        return this.executeStatus;
    }
}
