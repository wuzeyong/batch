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
