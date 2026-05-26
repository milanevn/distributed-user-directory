package com.ddb.userdirectory.controller;

import com.ddb.userdirectory.dto.DatasetGenerationResponse;
import com.ddb.userdirectory.service.DatasetGeneratorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dataset")
public class DatasetController {

    private final DatasetGeneratorService datasetGeneratorService;

    public DatasetController(DatasetGeneratorService datasetGeneratorService) {
        this.datasetGeneratorService = datasetGeneratorService;
    }

    @PostMapping("/generate")
    public DatasetGenerationResponse generateDataset(
            @RequestParam(defaultValue = "10000") int size,
            @RequestParam(name = "clearOldData", defaultValue = "true") boolean clearOldData) {
        return datasetGeneratorService.generateUsers(size, clearOldData);
    }

}
