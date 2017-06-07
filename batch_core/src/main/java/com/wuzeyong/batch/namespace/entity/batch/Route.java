package com.wuzeyong.batch.namespace.entity.batch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author WUZEYONG089
 */
@NoArgsConstructor
@Getter
@Setter
public class Route{

    private Unit currentUnit;

    private  String executeStatus;

    private Unit nextUnit;

    private  ExtendProperties props;


}
