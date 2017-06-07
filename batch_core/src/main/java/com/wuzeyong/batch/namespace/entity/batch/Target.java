package com.wuzeyong.batch.namespace.entity.batch;

import com.wuzeyong.batch.ExecuteContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WUZEYONG089
 */
@NoArgsConstructor
@Getter
@Setter
public class Target   {

    private static Logger LOGGER = LoggerFactory.getLogger(Target.class);

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
            LOGGER.error("目标：{}的根单元没有在路由表中定义！",getId());
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
