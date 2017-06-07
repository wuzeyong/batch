package com.wuzeyong.batch.namespace.handler.batch;

import com.wuzeyong.batch.namespace.parser.batch.BatchStrategyBeanDefinitionParser;
import com.wuzeyong.batch.namespace.parser.batch.BatchTaskBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Spring命名空间处理器.
 *
 * @author WUZEYONG089
 */
public final class BatchTaskNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("task", new BatchTaskBeanDefinitionParser());
        registerBeanDefinitionParser("paging-strategy", new BatchStrategyBeanDefinitionParser());
    }
}
