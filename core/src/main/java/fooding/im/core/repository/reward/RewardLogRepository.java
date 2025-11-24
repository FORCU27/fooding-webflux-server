package fooding.im.core.repository.reward;

import fooding.im.core.domain.reward.RewardLog;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RewardLogRepository extends R2dbcRepository<RewardLog, Long>, DRewardLogRepository {

}
