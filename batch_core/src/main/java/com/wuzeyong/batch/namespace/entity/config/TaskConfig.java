package com.wuzeyong.batch.namespace.entity.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author WUZEYONG
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaskConfig {

    private  String id;

    private  String name;

    private  String describe;

    private  Properties props;

    private  List<TargetConfig> targets = new ArrayList<TargetConfig>();
}
