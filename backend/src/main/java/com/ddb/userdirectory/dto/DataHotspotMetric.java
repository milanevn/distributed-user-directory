package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataHotspotMetric {

    private String shardName;
    private String range;
    private long userCount;
    private double dataPercentage;
    private boolean hotspotStatus;
    private String reason;
}
