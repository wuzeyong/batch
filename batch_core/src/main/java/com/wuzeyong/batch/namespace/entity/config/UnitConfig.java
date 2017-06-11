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
