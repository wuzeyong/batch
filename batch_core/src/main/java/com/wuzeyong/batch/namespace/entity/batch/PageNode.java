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
