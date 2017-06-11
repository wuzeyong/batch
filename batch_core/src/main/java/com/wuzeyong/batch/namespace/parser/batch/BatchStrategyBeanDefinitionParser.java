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
