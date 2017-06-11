package com.wuzeyong.batch.unit;

import com.wuzeyong.batch.namespace.entity.batch.AbstractBatchUnit;
import com.wuzeyong.batch.namespace.entity.batch.BusinessUnit;
import com.wuzeyong.batch.namespace.entity.batch.TaskSet;
import com.wuzeyong.batch.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by wzy on 2017/5/11.
 */
@Component
@Slf4j
@BusinessUnit
public class MyUnit1 extends AbstractBatchUnit<MyUnitTask,BaseResult> {

    @Override
    public TaskSet<MyUnitTask> produceTask() throws Exception {
        return null;
    }

    @Override
    public MyUnitTask decorateTask(TaskSet<MyUnitTask> taskSet) {
        MyUnitTask task  =  new MyUnitTask();
        task.setStr(taskSet.getStringFile(0));
        return task;
    }

    @Override
    public boolean consumeTask(MyUnitTask task) {
        //log.info("This is MyUnit1 consumeTask : {}",task.getStr());
        return true;
    }


}
