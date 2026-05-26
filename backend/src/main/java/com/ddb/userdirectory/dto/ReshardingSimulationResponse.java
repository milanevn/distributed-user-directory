package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReshardingSimulationResponse {

    private long totalUsers;

    private List<ShardDistributionDto> currentDistribution;

    private List<ShardDistributionDto> simulatedDistribution;

    /* max percentage - min percentage */
    private double imbalanceBefore;
    private double imbalanceAfter;

    private String conclusion;

}
