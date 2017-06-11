package com.wuzeyong.batch;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author WUZEYONG
 */
@Slf4j
@ContextConfiguration(locations = "classpath:namespace/BatchTask.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class BatchTaskNameSpaceTest  extends AbstractJUnit4SpringContextTests {

    @Resource
    private SpringBatchTask batchTask;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testSpringBatchTask(){
        TaskLauncher launcher = new TaskLauncher(batchTask);
        launcher.launcher();
    }

    @Test
    public void testDataSource() throws Exception{
        Connection connection = dataSource.getConnection();
        String sql = "select str,along,ainteger from batch_test";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String str = resultSet.getString(1);
            double along = resultSet.getDouble(2);
            Integer aInteger = resultSet.getInt(3);
            log.info("Test Data:{},{},{}",str,along,aInteger);
        }
    }

}
