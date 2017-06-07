package com.wuzeyong.batch.namespace.entity.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author WUZEYONG089
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TargetConfig {

    private String id;

    private String name;

    private String describe;

    private boolean enable;

    private Properties props;

    private DataSource dataSource;

    private List<UnitConfig> units = new ArrayList<UnitConfig>();

    private List<RouteConfig> routes = new ArrayList<RouteConfig>();

}
