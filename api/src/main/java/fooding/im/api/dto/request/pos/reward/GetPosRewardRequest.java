package fooding.im.api.dto.request.pos.reward;

import fooding.im.core.common.BasicSearch;
import fooding.im.core.domain.reward.RewardChannel;
import fooding.im.core.domain.reward.RewardStatus;
import fooding.im.core.domain.reward.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetPosRewardRequest extends BasicSearch {
    @NotNull
    @Schema( description = "가게 ID" )
    Long storeId;

    @Schema( description = "고객 전화번호" )
    String phoneNumber;

    @Schema( description = "리워드 상태" )
    RewardStatus status;

    @Schema( description = "리워드 채널" )
    RewardChannel channel;

    @Schema( description = "리워드 타입" )
    RewardType type;

    @Schema( description = "검색어" )
    String searchString;
}
