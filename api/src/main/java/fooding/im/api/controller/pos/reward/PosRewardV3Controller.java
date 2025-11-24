package fooding.im.api.controller.pos.reward;

import fooding.im.api.dto.request.pos.reward.GetPosRewardRequest;
import fooding.im.api.dto.response.pos.reward.GetPosRewardResponse;
import fooding.im.api.service.pos.reward.PosRewardService;
import fooding.im.core.common.ApiResult;
import fooding.im.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


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

}
