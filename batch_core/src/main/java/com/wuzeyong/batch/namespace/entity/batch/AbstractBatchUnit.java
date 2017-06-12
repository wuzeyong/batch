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


/**
 * @author WUZEYONG
 */
public abstract class AbstractBatchUnit<I,M,O> implements BatchUnit<I,M,O> {

    @Override
    public O checkCommModeResult() {
        return null;
    }

    @Override
    public O checkProducerResult() {
        return null;
    }

    @Override
    public O checkConsumerResult() {
        return null;
    }

    @Override
    public void handleFailedTask(M task) {
        return;
    }


}
