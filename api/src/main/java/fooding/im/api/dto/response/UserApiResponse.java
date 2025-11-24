package fooding.im.api.dto.response;

import fooding.im.core.domain.TestUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiResponse {
    private Long id;
    private String name;
    private String email;
    private boolean isAdult;

    public static UserApiResponse of(TestUser user) {
        return UserApiResponse.builder()
                .id( user.getId() )
                .name( user.getName() )
                .email( user.getEmail() )
                .isAdult( user.getAge() > 19 )
                .build();
    }
}
