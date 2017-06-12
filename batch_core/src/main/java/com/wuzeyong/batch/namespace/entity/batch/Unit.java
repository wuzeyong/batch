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
package com.wuzeyong.batch.namespace.entity.batch;

import com.google.common.base.Preconditions;
import com.wuzeyong.batch.ExecuteContext;
import com.wuzeyong.batch.constant.BatchCoreConstant;
import com.wuzeyong.batch.executor.BatchTaskExecutor;
import com.wuzeyong.batch.wrapper.CommExecutorWrapper;
import com.wuzeyong.batch.wrapper.ConsumerExecutorWrapper;
import com.wuzeyong.batch.wrapper.ProducerExecutorWrapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author WUZEYONG
 */
@Getter
@Setter
public class Unit {

    private String id;

    private String name;

    private String describe;

    private String allowManualExecute;

    private String pcMode;

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
        return BatchCoreConstant.Y.equals(pcMode)?
                new BatchTaskExecutor(executeContext.getExecutorEngine(),loadProducerWrapper(),loadConsumerWrapper(),pcMode).execute():
                new BatchTaskExecutor(executeContext.getExecutorEngine(),loadCommWrapper(),pcMode).execute();
    }


    private List<CommExecutorWrapper> loadCommWrapper() {
        String serverId = System.getProperty("batch.server.id");
        List<CommExecutorWrapper> commExecutorWrappers = new ArrayList<CommExecutorWrapper>();
        for(PageNode pageNode : pageNodes){
            if(pageNode.getServerId().equals(serverId)){
                CommExecutorWrapper commExecutorWrapper = new CommExecutorWrapper();
                commExecutorWrapper.setBatchUnit(batchUnit);
                commExecutorWrapper.setPageNode(pageNode);
                commExecutorWrapper.setExecutorType(BatchCoreConstant.EXECUTOR_TYPE_COMM);
                commExecutorWrapper.setDataSource(pageNode.getDataSource());
                commExecutorWrappers.add(commExecutorWrapper);
            }
        }
        return commExecutorWrappers;
    }

    /**
     * 直接根据配置获得消费者的执行数量
     * @return
     */
    private List<ConsumerExecutorWrapper> loadConsumerWrapper() {
        //TODO 怎么确定消费者的执行数量
        List<ProducerExecutorWrapper> producerExecutorWrappers = loadProducerWrapper();
        List<ConsumerExecutorWrapper> consumerExecutorWrappers = new ArrayList<ConsumerExecutorWrapper>(6);
        for(int i = 0 ; i < 6 ;i++){
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
    private List<ProducerExecutorWrapper> loadProducerWrapper() {
        String serverId = System.getProperty("batch.server.id");
        Preconditions.checkNotNull(serverId);
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
