package fooding.im.api.controller.pos.reward;

import fooding.im.api.dto.request.pos.reward.GetPosRewardRequest;
import fooding.im.api.dto.request.pos.reward.UpdateRewardLogRequest;
import fooding.im.api.dto.response.pos.reward.GetPosRewardResponse;
import fooding.im.api.service.pos.reward.PosRewardService;
import fooding.im.core.common.ApiResult;
import fooding.im.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v3/pos/reward")
@Tag( name = "PosRewardController.v3", description = "POS Reward 관련 컨트롤러(SSE)" )
public class PosRewardV3Controller {
    private final PosRewardService service;

    @GetMapping()
    @Operation( summary = "특정 스토어의 리워드 적립 조회" )
    public Mono<ApiResult<PageResponse<GetPosRewardResponse>>> list(
            @ModelAttribute GetPosRewardRequest request
    ){
        return service.list( request ).map( ApiResult::ok );
    }

    @PatchMapping("/{id}/cancel")
    @Operation( summary = "특정 리워드 로그에 대한 적립 취소" )
    public Mono<ApiResult<Void>> cancelReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardLogRequest request
    ){
        return service.cancel( id, request ).then( Mono.fromSupplier(ApiResult::ok));
    }

    @PatchMapping( "/{id}/approve" )
    @Operation( summary = "특정 리워드 로그에 대한 적립 허용" )
    public Mono<ApiResult<Void>> approveReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardLogRequest request
    ){
        return service.approve( id, request ).then( Mono.fromSupplier(ApiResult::ok) );
    }

    @PatchMapping( "/{id}/memo" )
    @Operation( summary = "특정 리워드 로그에 메모 변경" )
    public Mono<ApiResult<Void>> updateMemo(
            @PathVariable Long id,
            @RequestBody String memo
    ){
        return service.updateMemo( id, memo ).then( Mono.fromSupplier(ApiResult::ok) );
    }
}
