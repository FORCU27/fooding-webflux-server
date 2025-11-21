package fooding.im.core.repository.reward;

import fooding.im.core.domain.reward.RewardLog;
import fooding.im.core.domain.reward.RewardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DRewardLogRepository {
    Mono<Page<RewardLog>> list(String searchString, Pageable pageable, Long storeId, String phoneNumber, RewardStatus status);
    Mono<Long> listCount( String searchString, Long storeId, String phoneNumber, RewardStatus status );
}
