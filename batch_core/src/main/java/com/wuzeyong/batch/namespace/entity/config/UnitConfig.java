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
package com.wuzeyong.batch.namespace.entity.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Properties;

/**
 * @author WUZEYONG
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UnitConfig  {

    private  String id;

    private  String name;

    private  String describe;

    private  String allowManualExecute;

    private  String executeClass;

    private  PagingStrategyConfig pagingStrategy;

    private String pcMode;//生产者消费者模式

    private  Properties props;
}
