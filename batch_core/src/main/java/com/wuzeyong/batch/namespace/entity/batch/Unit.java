package com.wuzeyong.batch.namespace.entity.batch;

import com.wuzeyong.batch.ExecuteContext;
import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.BatchTaskExecutor;
import com.wuzeyong.batch.wrapper.ConsumerExecutorWrapper;
import com.wuzeyong.batch.wrapper.ProducerExecutorWrapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by WUZEYONG089 on 2017/5/4.
 */
@Getter
@Setter
public class Unit {

    private String id;

    private String name;

    private String describe;

    private String allowManualExecute;

    private Properties props;

    private List<PageNode> pageNodes;

    private Target target;

    private List<Route> routes;

    private BatchUnit batchUnit;

    private ExecuteContext executeContext;

    public final Unit nextUnit() {
        Unit u = null;
        Route r = null;
        String lastStatus = this.target.getLastExecuteStatus();
        for(int i = 0; i < this.routes.size(); ++i) {
            r = (Route)this.routes.get(i);
            if(this.getId().equals(r.getCurrentUnit().getId()) && lastStatus.equals(r.getExecuteStatus())) {
                u = r.getNextUnit();
                break;
            }
        }
        return u;
    }

    public String execute(){
        return new BatchTaskExecutor(executeContext.getExecutorEngine(),routeProducer(),getConsumer()).execute();
    }

    /**
     * 直接根据配置获得消费者的执行数量
     * @return
     */
    private List<ConsumerExecutorWrapper> getConsumer() {
        //TODO 怎么确定消费者的执行数量
        List<ProducerExecutorWrapper> producerExecutorWrappers = routeProducer();
        List<ConsumerExecutorWrapper> consumerExecutorWrappers = new ArrayList<ConsumerExecutorWrapper>(6);
        for(int i = 0 ; i < producerExecutorWrappers.size() ;i++){
            ConsumerExecutorWrapper consumerExecutorWrapper = new ConsumerExecutorWrapper();
            consumerExecutorWrapper.setBatchUnit(batchUnit);
            consumerExecutorWrapper.setExecutorType(BatchCoreConstant.EXECUTOR_TYPE_CONSUMER);
            consumerExecutorWrappers.add(consumerExecutorWrapper);
        }
        return consumerExecutorWrappers;
    }

    /**
     * 根据分页策略中的机器策略，获得生产者的执行
     * @return
     */
    private List<ProducerExecutorWrapper> routeProducer() {
        String serverId = System.getProperty("batch.server.id");
        List<ProducerExecutorWrapper> producerExecutorWrappers = new ArrayList<ProducerExecutorWrapper>();
        for(PageNode pageNode : pageNodes){
            if(pageNode.getServerId().equals(serverId)){
                ProducerExecutorWrapper producerExecutorWrapper = new ProducerExecutorWrapper();
                producerExecutorWrapper.setBatchUnit(batchUnit);
                producerExecutorWrapper.setPageNode(pageNode);
                producerExecutorWrapper.setExecutorType(BatchCoreConstant.EXECUTOR_TYPE_PRODUCER);
                producerExecutorWrapper.setDataSource(pageNode.getDataSource());
                producerExecutorWrappers.add(producerExecutorWrapper);
            }
        }
        return producerExecutorWrappers;
    }
}
