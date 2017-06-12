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

import com.wuzeyong.batch.ExecuteContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WUZEYONG
 */
@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class Target   {

    private String id;

    private String name;

    private String describe;

    private boolean enable;

    private DataSource defaultDataSource;

    private ExtendProperties props;

    private List<Unit> units = new ArrayList<Unit>();

    private List<Route> routes = new ArrayList<Route>();

    private Unit rootUnit;

    private Unit lastUnit;

    private Unit currentUnit;

    private String lastExecuteStatus="unexecute";

    private Route currentRoute;

    private ExecuteContext executeContext;

    public void execute(){
        currentUnit = getRootUnit();
        if(currentUnit == null){
            log.error("目标：{}的根单元没有在路由表中定义！",getId());
        }else {
            while(currentUnit != null){
                currentUnit.setExecuteContext(executeContext);
                lastExecuteStatus = currentUnit.execute();
                lastUnit = currentUnit;
                currentUnit = currentUnit.nextUnit();
            }
        }
    }

    private Unit getRootUnit() {
        if(this.rootUnit == null) {
            Unit u = null;
            if(!this.units.isEmpty()) {
                for(int i = 0; i < units.size(); ++i) {
                    if(units.get(i).nextUnit() != null) {
                        u = units.get(i);
                        break;
                    }
                }
            }
            this.rootUnit = u;
        }
        return this.rootUnit;
    }
}
