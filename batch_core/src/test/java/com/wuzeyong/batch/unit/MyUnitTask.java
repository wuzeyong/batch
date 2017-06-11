package com.wuzeyong.batch.unit;

import com.wuzeyong.batch.executor.BaseTask;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by wzy on 2017/5/11.
 */
@Setter
@Getter
@ToString
public class MyUnitTask implements BaseTask {
    private String str;

    private Long aLong;

    private Integer integer;
}