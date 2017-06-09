package com.wuzeyong.batch;


import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.wuzeyong.batch.namespace.entity.batch.*;
import com.wuzeyong.batch.namespace.entity.config.*;
import com.wuzeyong.batch.utils.AnnotationUtils;
import com.wuzeyong.batch.utils.DataConvertUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by WUZEYONG089 on 2017/5/4.
 */
@AllArgsConstructor
public class BatchRuleBuilder {

    private static Logger LOGGER = LoggerFactory.getLogger(BatchRuleBuilder.class);

    private final TaskConfig taskConfig;

    /**
     * 构建跑批规则
     * @return
     */
    public BatchRule build() {
        Task task = buildTask();
        BatchRule batchRule = new BatchRule();
        batchRule.setTask(task);
        return batchRule;
    }

    private Task buildTask() {
        Preconditions.checkArgument(!taskConfig.getTargets().isEmpty(), "Task: No targets config");
        Collection<Target> targets = buildTargets();
        Task task = new Task();
        task.setId(taskConfig.getId());
        task.setName(taskConfig.getName());
        task.setDescribe(taskConfig.getDescribe());
        task.setProps(new ExtendProperties(taskConfig.getProps()));
        task.setTargets(targets);
        return task;
    }

    private Collection<Target> buildTargets() {
        Collection<Target> targets = Collections2.transform(taskConfig.getTargets(), new Function<TargetConfig, Target>() {
            @Override
            public Target apply(TargetConfig targetConfig) {
                return buildTarget(targetConfig);
            }
        });
        return targets;
    }

    private Target buildTarget(TargetConfig targetConfig) {
        final List<BatchUnit> batchUnits = AnnotationUtils.getBatchUnitByAnnotation(BusinessUnit.class);
        final Target target = new Target();
        target.setId(targetConfig.getId());
        target.setName(targetConfig.getName());
        target.setDescribe(targetConfig.getDescribe());
        target.setEnable(targetConfig.isEnable());
        target.setDefaultDataSource(targetConfig.getDataSource());
        final List<Unit> units = Lists.transform(targetConfig.getUnits(), new Function<UnitConfig, Unit>() {
            @Override
            public Unit apply(UnitConfig unitConfig){
                return buildUnit(unitConfig,target,batchUnits);
            }
        });
        target.setUnits(units);
        List<Route> routes = Lists.transform(targetConfig.getRoutes(), new Function<RouteConfig, Route>() {
            @Override
            public Route apply(RouteConfig routeConfig) {
                return buildRoute(routeConfig,units);
            }
        });
        target.setRoutes(routes);
        return target;
    }

    private Route buildRoute(final RouteConfig routeConfig,Collection<Unit> units) {
        Route route = new Route();
        route.setProps(new ExtendProperties(routeConfig.getProps()));
        route.setExecuteStatus(routeConfig.getExecuteStatus());
        Collection<Unit> currentUnits = Collections2.filter(units, new Predicate<Unit>() {
            @Override
            public boolean apply(Unit unit) {
                String clazzName = unit.getId();
                String currentUnit = routeConfig.getCurrentUnit();
                return currentUnit.equals(clazzName);
            }
        });
        Preconditions.checkArgument(!currentUnits.isEmpty(), "CurrentUnit In Route No Exist");
        Preconditions.checkArgument(currentUnits.size() == 1, "Route Have More Then Two Same CurrentUnit");
        route.setCurrentUnit(currentUnits.iterator().next());

        Collection<Unit> nextUnits = Collections2.filter(units, new Predicate<Unit>() {
            @Override
            public boolean apply(Unit unit) {
                String clazzName = unit.getId();
                String nextUnit = routeConfig.getNextUnit();
                return nextUnit.equals(clazzName);
            }
        });
        Preconditions.checkArgument(!nextUnits.isEmpty(), "NextUnit In Route No Exist");
        Preconditions.checkArgument(nextUnits.size() == 1, "Route Have More Then Two Same Units");
        route.setNextUnit(nextUnits.iterator().next());
        return route;
    }

    private Unit buildUnit(UnitConfig unitConfig,Target target,List<BatchUnit> batchUnits) {
        Preconditions.checkArgument(!StringUtils.isEmpty(unitConfig.getExecuteClass()), "UnitConfig: No Execute Class");
        Unit unit = new Unit();
        unit.setId(unitConfig.getId());
        unit.setName(unitConfig.getName());
        unit.setAllowManualExecute(unitConfig.getAllowManualExecute());
        unit.setPcMode(unitConfig.getPcMode());
        unit.setDescribe(unitConfig.getDescribe());
        unit.setProps(unitConfig.getProps());
        unit.setTarget(target);
        unit.setRoutes(target.getRoutes());
        for(BatchUnit batchUnit : batchUnits){
            if(batchUnit.getClass().getSimpleName().equalsIgnoreCase(unitConfig.getExecuteClass())){
                unit.setBatchUnit(batchUnit);
                break;
            }
        }
        PagingStrategyConfig pagingStrategy = unitConfig.getPagingStrategy();
        if(pagingStrategy != null){
            List<PageNode> pageNodes = buildPageNodes(pagingStrategy,target);
            unit.setPageNodes(pageNodes);
        }
        return unit;
    }

    private List<PageNode> buildPageNodes(final PagingStrategyConfig pagingStrategy,final Target target) {
        List<PageNode> pageNodes = new ArrayList<PageNode>();
        List<MachineStrategyConfig> machineStrategyConfigs = pagingStrategy.getMachineStrategies();
        for(MachineStrategyConfig  machineStrategyConfig : machineStrategyConfigs){
            final MachineStrategyConfig finalMachineStrategyConfig = machineStrategyConfig;
            List<Integer> handlePages = Lists.transform(
                    Splitter.on(",").trimResults().splitToList(machineStrategyConfig.getHandlePages()),
                    new Function<String, Integer>() {
                        @Override
                        public Integer apply(String s) {
                            return DataConvertUtil.toInt(s);
                        }
                    }
            );
            List<PageNode> pageNodeInMachineStrategy = Lists.transform(handlePages, new Function<Integer, PageNode>() {
                @Override
                public PageNode apply(Integer integer) {
                    PageNode pageNode = new PageNode();
                    pageNode.setServerId(finalMachineStrategyConfig.getServerId());
                    pageNode.setPageMode(pagingStrategy.getPageMode());
                    pageNode.setTotalPaging(pagingStrategy.getTotalPaging());
                    DataSource machineStrategyDataSource = finalMachineStrategyConfig.getDataSource();
                    if(machineStrategyDataSource == null){
                        machineStrategyDataSource = target.getDefaultDataSource();
                    }
                    pageNode.setDataSource(machineStrategyDataSource);
                    pageNode.setPageNo(integer);
                    return pageNode;
                }
            });
            pageNodes.addAll(pageNodeInMachineStrategy);
        }
        return pageNodes;
    }
}
