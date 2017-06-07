package com.wuzeyong.batch;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.annotation.Resource;

/**
 * Created by wzy on 2017/5/6.
 */
@ContextConfiguration(locations = "classpath:namespace/BatchTask.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class BatchTaskNameSpaceTest  extends AbstractJUnit4SpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchTaskNameSpaceTest.class);

    @Resource
    private SpringBatchTask batchTask;

    @Test
    public void testSpringBatchTask(){
        TaskLauncher launcher = new TaskLauncher(batchTask);
        launcher.launcher();
    }

}
