package com.wuzeyong.batch.unit;

import com.wuzeyong.batch.executor.BaseTask;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author WUZEYONG
 */
@Setter
@Getter
@ToString
public class MyUnitTask implements BaseTask {

    private String str;

    private Double aLong;

    private Integer integer;
}