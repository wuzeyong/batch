package com.wuzeyong.batch.namespace.entity.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WUZEYONG089
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PagingStrategyConfig {

    private  String id;

    private  String pageMode;

    private  int totalPaging;

    private List<MachineStrategyConfig> machineStrategies = new ArrayList<MachineStrategyConfig>();

}
