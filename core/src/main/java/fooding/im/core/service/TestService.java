package fooding.im.core.service;

import fooding.im.core.domain.TestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final List<TestUser> userList = List.of(
        TestUser.builder().id( 1L ).name( "Kim" ).email( "kim@gmail.com" ).age( 20 ).build(),
        TestUser.builder().id( 1L ).name( "Lee" ).email( "lee@gmail.com" ).age( 22 ).build(),
        TestUser.builder().id( 1L ).name( "Park" ).email( "park@gmail.com" ).age( 25 ).build(),
        TestUser.builder().id( 1L ).name( "Choi" ).email( "choi@gmail.com" ).age( 20 ).build(),
        TestUser.builder().id( 1L ).name( "Jeong" ).email( "jeong@gmail.com" ).age( 18 ).build()
    );

    public Mono<TestUser> findById( int index ) {
        TestUser user = userList.get( index );
        return Mono.just( user );
    }

    public Flux<TestUser> list(){
        return Flux.fromIterable( userList );
    }
}
