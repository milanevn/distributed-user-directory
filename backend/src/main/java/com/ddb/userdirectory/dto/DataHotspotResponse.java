package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DataHotspotResponse {

    private long totalUsers;
    private double thresholdPercentage;
    private boolean hasHotspot;
    private List<DataHotspotMetric> shards;
    private String message;

}
