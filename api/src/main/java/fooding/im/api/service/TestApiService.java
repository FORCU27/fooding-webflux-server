package fooding.im.api.service;

import fooding.im.api.dto.response.UserApiResponse;
import fooding.im.core.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TestApiService {
    private final TestService service;

    public Mono<UserApiResponse> findById( int id ){
        return service.findById( id ).map(UserApiResponse::of);
    }

    public Flux<UserApiResponse> findAll(){
        return service.list().map(UserApiResponse::of);
    }

}
