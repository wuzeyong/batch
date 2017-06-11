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

    //��ҳģʽ��single/multi
    private String pageMode;

    //�ܹ���ҳ��
    private int totalPaging;

    //������id
    private String serverId;

    //ҳ��
    private int pageNo;

    //����Դ
    private DataSource dataSource;

}
