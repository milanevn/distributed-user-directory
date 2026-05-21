package com.ddb.userdirectory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetGenerationResponse {

    private int totalGenerated;

    private int countM;
    private int countS;
    private int countOther;

    private Map<String, Long> insertedPerShard;

    private boolean clearedBeforeGenerate;

    private String message;
}
