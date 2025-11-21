package fooding.im.core.repository.reward;

import fooding.im.core.domain.reward.RewardPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DRewardPointRepository {
    Mono<Page<RewardPoint>> list(String searchString, Pageable pageable, Long storeId, String phoneNumber );
}
