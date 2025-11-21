package fooding.im.core.repository.reward;

import fooding.im.core.domain.reward.RewardPoint;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RewardPointRepository extends R2dbcRepository<RewardPoint, Long>, DRewardPointRepository {

    Mono<RewardPoint> findByUserIdAndStoreIdAndDeletedIsFalse(long userId, long storeId);
    Mono<RewardPoint> findByPhoneNumberAndStoreIdAndDeletedIsFalse(String phoneNumber, long storeId);
}
