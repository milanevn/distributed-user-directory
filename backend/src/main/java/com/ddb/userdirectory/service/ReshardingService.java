package com.ddb.userdirectory.service;

import com.ddb.userdirectory.dto.CurrentShardRangeDto;
import com.ddb.userdirectory.dto.ProposedShardRangeDto;
import com.ddb.userdirectory.dto.ReshardingPlanResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReshardingService {

    public ReshardingPlanResponse getReshardingPlan(){
        List<CurrentShardRangeDto> currentRanges = List.of(
                new CurrentShardRangeDto(
                        "AG",
                        "A-G",
                        "Shard này thường ít dữ liệu hơn vì dataset chủ yếu tập trung vào M và S."
                ),
                new CurrentShardRangeDto(
                        "HN",
                        "H-N",
                        "Shard này chứa username bắt đầu bằng M, nên dễ bị quá tải."
                ),
                new CurrentShardRangeDto(
                        "OZ",
                        "O-Z",
                        "Shard này chứa username bắt đầu bằng S, nên dễ bị quá tải."
                )
        );

        List<ProposedShardRangeDto> proposedRanges = List.of(
                new ProposedShardRangeDto(
                        "SHARD_1",
                        "A-L",
                        "Gom các chữ cái ít bị lệch vào một shard chung."
                ),
                new ProposedShardRangeDto(
                        "SHARD_2",
                        "M",
                        "Tách riêng M vì dataset có nhiều username bắt đầu bằng M."
                ),
                new ProposedShardRangeDto(
                        "SHARD_3",
                        "N-R",
                        "Giữ nhóm trung gian để giảm tải cho shard chứa S."
                ),
                new ProposedShardRangeDto(
                        "SHARD_4",
                        "S",
                        "Tách riêng S vì dataset có nhiều username bắt đầu bằng S."
                ),
                new ProposedShardRangeDto(
                        "SHARD_5",
                        "T-Z",
                        "Gom phần còn lại sau S vào shard riêng."
                )
        );

        return new ReshardingPlanResponse(
                "Đề xuất cách chia lại shard để giảm hotspot do dữ liệu username bị lệch.",
                "Range-based sharding ban đầu: A-G, H-N, O-Z",
                currentRanges,
                "Dataset có 70% username bắt đầu bằng M hoặc S. Vì M thuộc H-N và S thuộc O-Z",
                "Tách các khóa nóng M và S ra shard riêng",
                proposedRanges,
                "Chiến lược mới giúp cô lập hai nhóm dữ liệu lớn M và S, tránh việc chúng bị gộp chung với quá nhiều chữ cái khác. Nhờ đó tải dữ liệu được chia nhỏ hơn và dễ mở rộng hơn ",
                true
        );
    }
}
