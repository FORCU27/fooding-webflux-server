package fooding.im.core.service.reward;

import fooding.im.core.domain.reward.RewardChannel;
import fooding.im.core.domain.reward.RewardLog;
import fooding.im.core.domain.reward.RewardStatus;
import fooding.im.core.domain.reward.RewardType;
import fooding.im.core.global.exception.ApiException;
import fooding.im.core.global.exception.ErrorCode;
import fooding.im.core.repository.reward.RewardLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class RewardLogService {
    private final RewardLogRepository repository;

    public Mono<Page<RewardLog>> list(String searchString, Pageable pageable, Long storeId, String phoneNumber, RewardStatus status){
        return repository.list( searchString, pageable, storeId, phoneNumber, status );
    }

    public Mono<RewardLog> findById( long id ){
        return repository.findById( id ).switchIfEmpty( Mono.error(new ApiException(ErrorCode.REWARD_LOG_NOT_FOUND)));
    }

    public Mono<Long> countList(String searchString, Long storeId, String phoneNumber, RewardStatus status){
        return repository.listCount( searchString, storeId, phoneNumber, status);
    }

    public Mono<Long> create(Long storeId, String phoneNumber, int point, RewardStatus status, RewardType type, RewardChannel channel, String memo ){
        RewardLog rewardLog = RewardLog.builder()
                .storeId( storeId )
                .phoneNumber( phoneNumber )
                .point( point )
                .status( status )
                .type( type )
                .channel( channel )
                .memo( memo )
                .build();
        return repository.save(rewardLog)
                .map(RewardLog::getId)
                .onErrorMap(ex -> new ApiException(ErrorCode.REWARD_LOG_CREATION_ERROR));
    }

    public Mono<Void> delete( long id, Long deletedBy ){
        Mono<RewardLog> rewardLogMono = repository.findById( id ).switchIfEmpty(Mono.error(new ApiException(ErrorCode.REWARD_NOT_FOUND)));
        return rewardLogMono.flatMap( rewardlog -> {
            rewardlog.delete( deletedBy );
            return repository.save( rewardlog );
        }).then();
    }

    public Mono<Void> updateMemo( long id, String memo ){
        Mono<RewardLog> rewardLogMono = repository.findById( id ).switchIfEmpty(Mono.error(new ApiException(ErrorCode.REWARD_NOT_FOUND)));
        return rewardLogMono.flatMap( rewardLog -> {
            rewardLog.updateMemo( memo );
            return repository.save( rewardLog );
        }).then();
    }
}
