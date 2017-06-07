package com.wuzeyong.batch.namespace.entity.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Properties;

/**
 * @author WUZEYONG089
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RouteConfig {

    private String currentUnit;

    private  String executeStatus;

    private String nextUnit;

    private  Properties props;

}
