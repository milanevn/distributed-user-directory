package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReshardingPlanResponse {

    private String goal;

    private String currentStrategy;

    private List<CurrentShardRangeDto> currentRanges;

    private String detectedProblem;

    private String proposedStrategy;

    private List<ProposedShardRangeDto> proposedRanges;

    private String improvementReason;

    private boolean requiresDataMigration;
}
