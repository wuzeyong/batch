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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;

/**
 * @author WUZEYONG
 */
@Getter
@Setter
@EqualsAndHashCode
public class PageNode {

    //分页模式：single/multi
    private String pageMode;

    //总共分页数
    private int totalPaging;

    //服务器id
    private String serverId;

    //页数
    private int pageNo;

    //数据源
    private DataSource dataSource;

}
