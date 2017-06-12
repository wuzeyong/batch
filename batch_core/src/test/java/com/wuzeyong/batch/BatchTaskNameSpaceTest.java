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
