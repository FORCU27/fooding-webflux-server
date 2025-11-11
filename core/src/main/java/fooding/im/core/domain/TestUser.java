package fooding.im.core.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestUser {
    private Long id;
    private String name;
    private String email;
    private int age;
}
