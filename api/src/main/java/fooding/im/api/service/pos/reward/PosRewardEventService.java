package fooding.im.api.service.pos.reward;

import fooding.im.api.dto.request.pos.reward.CreatePosRewardRequest;
import fooding.im.api.dto.request.pos.reward.GetPosRewardRequest;
import fooding.im.api.dto.request.pos.reward.UpdateRewardLogRequest;
import fooding.im.api.dto.response.pos.reward.GetPosRewardResponse;
import fooding.im.api.publisher.UpdateRewardListEventPublisher;
import fooding.im.core.common.PageInfo;
import fooding.im.core.common.PageResponse;
import fooding.im.core.domain.reward.RewardChannel;
import fooding.im.core.domain.reward.RewardStatus;
import fooding.im.core.domain.reward.RewardType;
import fooding.im.core.service.reward.RewardLogService;
import fooding.im.core.service.reward.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PosRewardEventService {
    private final RewardLogService logService;
    private final RewardService rewardService;
    private final UpdateRewardListEventPublisher eventPublisher;

    public Flux<GetPosRewardResponse> subscribeRewardEvent() {
        return eventPublisher.getUpdatedRewardList();
    }

    public Mono<Void> createReward(CreatePosRewardRequest request, Long storeId ) {
        return rewardService.findByStoreIdAndPhoneNumber(storeId, request.getPhoneNumber())
                .hasElement()
                .flatMap(existed -> {
                    if (existed) {
                        return rewardService.addPoint(
                                request.getPhoneNumber(),
                                storeId,
                                request.getPoint()
                        );
                    } else {
                        return rewardService.create(
                                storeId,
                                request.getPhoneNumber(),
                                request.getPoint(),
                                request.getUserId()
                        );
                    }
                })
                .then(
                        logService.create(
                                storeId,
                                request.getPhoneNumber(),
                                request.getPoint(),
                                RewardStatus.EARNED,
                                RewardType.VISIT,
                                RewardChannel.STORE,
                                null
                        )
                )
                .doOnNext(rewardLog -> {
                    eventPublisher.publishRewardListUpdate(
                            GetPosRewardResponse.of(rewardLog)
                    );
                })
                .then();
    }

    public Mono<PageResponse<GetPosRewardResponse>> list(GetPosRewardRequest request){
        return logService.list(
                        request.getSearchString(),
                        request.getPageable(),
                        request.getStoreId(),
                        request.getPhoneNumber(),
                        request.getStatus()
                ).map( page -> page.map( GetPosRewardResponse::of ) )
                .map( page -> PageResponse.of( page.getContent(), PageInfo.of( page ) ) );
    }

    public Mono<Void> cancel(Long rewardLogId, UpdateRewardLogRequest request){
        return logService.findById(rewardLogId)
                .flatMap(log -> {
                    if (log.getStatus() == RewardStatus.CANCELED) {
                        return Mono.empty();
                    }

                    return logService.create(
                            log.getStoreId(),
                            log.getPhoneNumber(),
                            log.getPoint(),
                            RewardStatus.CANCELED,
                            log.getType(),
                            log.getChannel(),
                            log.getMemo()
                    ).flatMap(rewardLog -> {
                        eventPublisher.publishRewardListUpdate(
                                GetPosRewardResponse.of(rewardLog)
                        );
                        return rewardService.usePoint(
                                log.getPhoneNumber(),
                                log.getStoreId(),
                                log.getPoint()
                        );
                    });
                })
                .then();
    }

    public Mono<Void> approve( Long rewardLogId, UpdateRewardLogRequest request ){
        return logService.findById( rewardLogId )
                .flatMap( log -> {
                    if( log.getStatus() == RewardStatus.EARNED ) return Mono.empty();

                    return logService.create(
                            log.getStoreId(),
                            log.getPhoneNumber(),
                            log.getPoint(),
                            RewardStatus.EARNED,
                            log.getType(),
                            log.getChannel(),
                            request.getMemo()
                    );
                })
                .doOnNext( rewardLog -> {
                    eventPublisher.publishRewardListUpdate( GetPosRewardResponse.of( rewardLog ) );
                })
                .then();
    }

    public Mono<Void> updateMemo( Long id, String memo ){
        return logService.updateMemo( id, memo ).then();
    }
}
