package com.wuzeyong.batch.namespace.entity.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.sql.DataSource;

/**
 * @author WUZEYONG
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MachineStrategyConfig {

    private String serverId;

    private String handlePages;

    private DataSource dataSource;
}
