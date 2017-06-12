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
package com.wuzeyong.batch.namespace.parser.batch;

import com.google.common.base.Strings;
import com.wuzeyong.batch.namespace.constants.batch.PagingStrategyBeanDefinitionParserTag;
import com.wuzeyong.batch.namespace.entity.config.MachineStrategyConfig;
import com.wuzeyong.batch.namespace.entity.config.PagingStrategyConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.List;

/**
 * 基于Spring命名空间的解析器.
 *®
 * @author WUZEYONG
 */
public class BatchStrategyBeanDefinitionParser extends AbstractBeanDefinitionParser {
    
    @Override
    protected AbstractBeanDefinition parseInternal(final Element element, final ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(PagingStrategyConfig.class);
        builder.addPropertyValue("id",element.getAttribute(PagingStrategyBeanDefinitionParserTag.ID_TAG));
        builder.addPropertyValue("pageMode",element.getAttribute(PagingStrategyBeanDefinitionParserTag.PAGING_MODE_TAG));
        builder.addPropertyValue("totalPaging", element.getAttribute(PagingStrategyBeanDefinitionParserTag.TOTAL_PAGING_TAG));
        builder.addPropertyValue("machineStrategies",parseMachineStrategies(element, parserContext));
        return builder.getBeanDefinition();
    }

    private List<BeanDefinition> parseMachineStrategies(Element element, ParserContext parserContext) {
        Element machineStrategiesElement = DomUtils.getChildElementByTagName(element, PagingStrategyBeanDefinitionParserTag.MACHINE_STRATEGIES_TAG);
        if (null == machineStrategiesElement) {
            return Collections.emptyList();
        }
        List<Element> machineStrategyElements = DomUtils.getChildElementsByTagName(machineStrategiesElement, PagingStrategyBeanDefinitionParserTag.MACHINE_STRATEGY_TAG);
        List<BeanDefinition> result = new ManagedList<BeanDefinition>(machineStrategyElements.size());
        for(Element machineStrategyElement : machineStrategyElements){
            BeanDefinitionBuilder machineStrategyFactory = BeanDefinitionBuilder.rootBeanDefinition(MachineStrategyConfig.class);
            machineStrategyFactory.addPropertyValue("serverId", machineStrategyElement.getAttribute(PagingStrategyBeanDefinitionParserTag.SERVER_ID_TAG));
            machineStrategyFactory.addPropertyValue("handlePages", machineStrategyElement.getAttribute(PagingStrategyBeanDefinitionParserTag.HANDLE_PAGING_TAG));
            String dataSource = machineStrategyElement.getAttribute(PagingStrategyBeanDefinitionParserTag.DATA_SOURCE_TAG);
            if(!Strings.isNullOrEmpty(dataSource)){
                machineStrategyFactory.addPropertyReference("dataSource", dataSource);
            }
            result.add(machineStrategyFactory.getBeanDefinition());
        }
        return result;
    }

}
