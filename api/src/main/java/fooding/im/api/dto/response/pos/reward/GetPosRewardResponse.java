package fooding.im.api.dto.response.pos.reward;

import fooding.im.core.domain.reward.RewardChannel;
import fooding.im.core.domain.reward.RewardLog;
import fooding.im.core.domain.reward.RewardStatus;
import fooding.im.core.domain.reward.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetPosRewardResponse(
        @Schema( description = "ID" )
        Long id,

        @Schema( description = "고객 전화번호" )
        String phoneNumber,

        @Schema( description = "적립 포인트" )
        int point,

        @Schema( description = "적립 상태" )
        RewardStatus status,

        @Schema( description = "리워드 타입" )
        RewardType type,

        @Schema( description = "리워드 채널" )
        RewardChannel channel,

        @Schema( description = "적립 시간" )
        LocalDateTime createdAt
) {
        public static GetPosRewardResponse of(RewardLog rewardLog){
                return GetPosRewardResponse.builder()
                        .id( rewardLog.getId() )
                        .phoneNumber(rewardLog.getPhoneNumber())
                        .point(rewardLog.getPoint())
                        .status(rewardLog.getStatus())
                        .type(rewardLog.getType())
                        .channel(rewardLog.getChannel())
                        .createdAt(rewardLog.getCreatedAt())
                        .build();
        }
}
