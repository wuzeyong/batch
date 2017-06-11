package com.wuzeyong.batch.namespace.entity.batch;

import com.wuzeyong.batch.ExecuteContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author WUZEYONG
 */
@NoArgsConstructor
@Getter
@Setter
public class Task {

    private String id;

    private String name;

    private String describe;

    private ExtendProperties props;

    private Collection<Target> targets = new ArrayList<Target>();

    private ExecuteContext executeContext;

    public void execute(){
        for(Target target : targets){
            target.setExecuteContext(executeContext);
            target.execute();
        }
    }
}
