package fooding.im.api.controller;

import fooding.im.api.dto.response.UserApiResponse;
import fooding.im.api.service.TestApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/test")
@Tag( name = "Test Controller")
public class TestController {
    private final TestApiService testService;

    @GetMapping()
    @Operation( summary = "Test API" )
    public ResponseEntity<Map<String, Boolean>> testApi() {
        return ResponseEntity.ok(Map.of( "result", true ) );
    }

    @GetMapping("/mono/{id}")
    @Operation( summary = "Mono Test API" )
    public ResponseEntity<Mono<UserApiResponse>> testMono(
            @PathVariable int id
    ){
        return ResponseEntity.ok( testService.findById( id ) );
    }

    @GetMapping( "/flux" )
    @Operation( summary = "Flux Test API" )
    public ResponseEntity<Flux<UserApiResponse>> testFlux(){
        return ResponseEntity.ok( testService.findAll() );
    }
}
