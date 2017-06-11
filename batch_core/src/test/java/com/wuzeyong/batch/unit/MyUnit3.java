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
public class MyUnit3 extends AbstractBatchUnit<ResultSet,MyUnitTask,BaseResult> {

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

        }
        return task;
    }

    @Override
    public boolean consumeTask(MyUnitTask task) {
        log.info("MyUnit3 consume task :{}",task.toString());
        return true;
    }

    @Override
    public BaseResult checkCommModeResult() {
        CommBaseResult commBaseResult = new CommBaseResult();
        commBaseResult.setExecuteStatus(BatchCoreConstant.EXECUTE_STATUS_SUCCESSFUL);
        return commBaseResult;
    }
}