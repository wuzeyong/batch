/*
 * Copyright 2017 The Batch Framework Author.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package com.wuzeyong.batch.unit;

import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.namespace.entity.batch.AbstractBatchUnit;
import com.wuzeyong.batch.result.BaseResult;
import com.wuzeyong.batch.result.CommBaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author WUZEYONG
 */
@Slf4j
@Component
public class MyUnit1 extends AbstractBatchUnit<ResultSet,MyUnitTask,BaseResult>{

    @Autowired
    private DataSource dataSource;

    @Override
    public ResultSet produceSet() throws Exception {
        Connection connection = dataSource.getConnection();
        String sql = "select str,along,ainteger from batch_test";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    @Override
    public MyUnitTask decorateTask(ResultSet resultSet) {
        MyUnitTask task  =  new MyUnitTask();
        try {
            task.setStr(resultSet.getString(1));
            task.setALong(resultSet.getDouble(2));
            task.setInteger(resultSet.getInt(3));
        } catch (SQLException e) {
            log.error("Decore Task When Error Happen!",e);
        }
        return task;
    }

    @Override
    public boolean consumeTask(MyUnitTask task) {
        log.info("MyUnit1 consume task :{}",task.toString());
        return true;
    }

    @Override
    public void handleFailedTask(MyUnitTask task) {
        log.info("Unit test can bear failed task,so do nothing!");
        return;
    }

    @Override
    public BaseResult checkCommModeResult() {
        CommBaseResult commBaseResult = new CommBaseResult();
        commBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL);
        return commBaseResult;
    }
}
