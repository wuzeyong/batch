package com.wuzeyong.batch.result;

import lombok.Setter;

/**
 * @author WUZEYONG
 */
public abstract class AbstractBaseResult implements BaseResult {

    @Setter
    private String executeStatus;

    @Override
    public String getExecuteStatus() {
        return this.executeStatus;
    }
}
