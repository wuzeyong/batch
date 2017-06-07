package com.wuzeyong.batch;

import com.wuzeyong.batch.namespace.entity.batch.Task;
import lombok.AllArgsConstructor;

/**
 * Created by wzy on 2017/5/14.
 */
@AllArgsConstructor
public class TaskLauncher {

    private final SpringBatchTask batchTask;

    public void launcher(){
        Task task = batchTask.getExecuteContext().getBatchRule().getTask();
        task.setExecuteContext(batchTask.getExecuteContext());
        task.execute();
    }

}
