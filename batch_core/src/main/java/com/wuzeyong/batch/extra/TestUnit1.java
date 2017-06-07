package com.wuzeyong.batch.extra;

import com.wuzeyong.batch.namespace.entity.batch.AbstractBatchUnit;
import com.wuzeyong.batch.namespace.entity.batch.BusinessUnit;
import com.wuzeyong.batch.result.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzy on 2017/5/11.
 */
@BusinessUnit
public class TestUnit1 extends AbstractBatchUnit<TestUnitTask,BaseResult> {

    private static Logger LOGGER = LoggerFactory.getLogger(TestUnit1.class);


    @Override
    public List<TestUnitTask> produceTasks() throws Exception {
        List<TestUnitTask> source = new ArrayList<TestUnitTask>();
        for(int i = 0; i<1000 ;i++){
            TestUnitTask task  =  new TestUnitTask();
            task.setStr(String.valueOf(i));
            source.add(task);
        }
        return source;
    }

    @Override
    public boolean consumeTask(TestUnitTask task) {
        //LOGGER.info("This is TestUnit1 consumeTask : {}",task.getStr());
        return true;
    }


}
