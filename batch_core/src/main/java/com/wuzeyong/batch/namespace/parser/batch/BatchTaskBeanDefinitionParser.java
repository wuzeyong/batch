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
import com.wuzeyong.batch.SpringBatchTask;
import com.wuzeyong.batch.namespace.constants.batch.BatchTaskBeanDefinitionParserTag;
import com.wuzeyong.batch.namespace.entity.config.RouteConfig;
import com.wuzeyong.batch.namespace.entity.config.TargetConfig;
import com.wuzeyong.batch.namespace.entity.config.TaskConfig;
import com.wuzeyong.batch.namespace.entity.config.UnitConfig;
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
import java.util.Properties;

/**
 * 基于Spring命名空间的解析器.
 *®
 * @author WUZEYONG
 */
public class BatchTaskBeanDefinitionParser extends AbstractBeanDefinitionParser {
    
    @Override
    protected AbstractBeanDefinition parseInternal(final Element element, final ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringBatchTask.class);
        factory.addConstructorArgValue(parseTask(element, parserContext));
        factory.addConstructorArgValue(parseProps(element, parserContext));
        return factory.getBeanDefinition();
    }

    private BeanDefinition parseTask(final Element element, final ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(TaskConfig.class);
        factory.addPropertyValue("id",element.getAttribute(BatchTaskBeanDefinitionParserTag.ID_TAG));
        factory.addPropertyValue("name",element.getAttribute(BatchTaskBeanDefinitionParserTag.NAME_TAG));
        factory.addPropertyValue("describe",element.getAttribute(BatchTaskBeanDefinitionParserTag.DESCRIBE_TAG));
        factory.addPropertyValue("targets", parseTargets(element, parserContext));
        factory.addPropertyValue("props", parseProps(element, parserContext));
        return factory.getBeanDefinition();
    }


    private List<BeanDefinition> parseTargets(Element element, ParserContext parserContext) {
        Element targetsElement = DomUtils.getChildElementByTagName(element, BatchTaskBeanDefinitionParserTag.TARGETS_TAG);
        if (null == targetsElement) {
            return Collections.emptyList();
        }
        List<Element> targetElements = DomUtils.getChildElementsByTagName(targetsElement, BatchTaskBeanDefinitionParserTag.TARGET_TAG);
        List<BeanDefinition> result = new ManagedList<BeanDefinition>(targetElements.size());
        for(Element targetElement : targetElements){
            BeanDefinitionBuilder targetFactory = BeanDefinitionBuilder.rootBeanDefinition(TargetConfig.class);
            targetFactory.addPropertyValue("id",targetElement.getAttribute(BatchTaskBeanDefinitionParserTag.ID_TAG));
            targetFactory.addPropertyValue("name", targetElement.getAttribute(BatchTaskBeanDefinitionParserTag.NAME_TAG));
            targetFactory.addPropertyValue("describe", targetElement.getAttribute(BatchTaskBeanDefinitionParserTag.DESCRIBE_TAG));
            targetFactory.addPropertyValue("enable", targetElement.getAttribute(BatchTaskBeanDefinitionParserTag.ENABLE_TAG));
            String dataSource = targetElement.getAttribute(BatchTaskBeanDefinitionParserTag.DATASOURCE_TAG);
            if(!Strings.isNullOrEmpty(dataSource)){
                targetFactory.addPropertyReference("dataSource", dataSource);
            }
            targetFactory.addPropertyValue("units", parseUnits(targetElement, parserContext));
            targetFactory.addPropertyValue("routes",parseRoutes(targetElement, parserContext));
            targetFactory.addPropertyValue("props", parseProps(element, parserContext));
            result.add(targetFactory.getBeanDefinition());
        }
        return result;
    }


    private List<BeanDefinition> parseUnits(Element element, ParserContext parserContext) {
        Element unitsElement = DomUtils.getChildElementByTagName(element, BatchTaskBeanDefinitionParserTag.UNITS_TAG);
        if (null == unitsElement) {
            return Collections.emptyList();
        }
        List<Element> unitElements = DomUtils.getChildElementsByTagName(unitsElement, BatchTaskBeanDefinitionParserTag.UNIT_TAG);
        List<BeanDefinition> result = new ManagedList<BeanDefinition>(unitElements.size());
        for(Element unitElement : unitElements){
            BeanDefinitionBuilder unitFactory = BeanDefinitionBuilder.rootBeanDefinition(UnitConfig.class);
            unitFactory.addPropertyValue("id",unitElement.getAttribute(BatchTaskBeanDefinitionParserTag.ID_TAG));
            unitFactory.addPropertyValue("name",unitElement.getAttribute(BatchTaskBeanDefinitionParserTag.NAME_TAG));
            unitFactory.addPropertyValue("describe",unitElement.getAttribute(BatchTaskBeanDefinitionParserTag.DESCRIBE_TAG));
            unitFactory.addPropertyValue("allowManualExecute", unitElement.getAttribute(BatchTaskBeanDefinitionParserTag.ALLOW_MANUAL_EXECUTE_TAG));
            unitFactory.addPropertyValue("executeClass", unitElement.getAttribute(BatchTaskBeanDefinitionParserTag.EXECUTE_CLASS_TAG));
            unitFactory.addPropertyValue("pcMode",unitElement.getAttribute(BatchTaskBeanDefinitionParserTag.PRODUCER_CONSUMER_TAG));
            String pagingStrategy = unitElement.getAttribute(BatchTaskBeanDefinitionParserTag.PAGING_STRATEGY_TAG);
            if(!Strings.isNullOrEmpty(pagingStrategy)){
                unitFactory.addPropertyReference("pagingStrategy", pagingStrategy);
            }
            unitFactory.addPropertyValue("props", parseProps(element, parserContext));
            result.add(unitFactory.getBeanDefinition());
        }
        return result;
    }

    private List<BeanDefinition> parseRoutes(Element element, ParserContext parserContext) {
        Element routsElement = DomUtils.getChildElementByTagName(element, BatchTaskBeanDefinitionParserTag.ROUTES_TAG);
        if (null == routsElement) {
            return Collections.emptyList();
        }
        List<Element> routElements = DomUtils.getChildElementsByTagName(routsElement, BatchTaskBeanDefinitionParserTag.ROUTE_TAG);
        List<BeanDefinition> result = new ManagedList<BeanDefinition>(routElements.size());
        for(Element routElement : routElements){
            BeanDefinitionBuilder routFactory = BeanDefinitionBuilder.rootBeanDefinition(RouteConfig.class);
            routFactory.addPropertyValue("currentUnit", routElement.getAttribute(BatchTaskBeanDefinitionParserTag.CURRENT_UNIT_TAG));
            routFactory.addPropertyValue("executeStatus",routElement.getAttribute(BatchTaskBeanDefinitionParserTag.EXECUTE_STATUS_TAG));
            routFactory.addPropertyValue("nextUnit", routElement.getAttribute(BatchTaskBeanDefinitionParserTag.NEXT_UNIT_TAG));
            routFactory.addPropertyValue("props", parseProps(element, parserContext));
            result.add(routFactory.getBeanDefinition());
        }
        return result;
    }


    private Properties parseProps(final Element element, final ParserContext parserContext) {
        Element propsElement = DomUtils.getChildElementByTagName(element, BatchTaskBeanDefinitionParserTag.PROPERTIES_TAG);
        return null == propsElement ? new Properties() : parserContext.getDelegate().parsePropsElement(propsElement);
    }
}
