package fooding.im.core.domain.reward;

import fooding.im.core.domain.BaseEntity;
import fooding.im.core.global.exception.ApiException;
import fooding.im.core.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Table( name="reward_point" )
@Getter
@Setter
@NoArgsConstructor
public class RewardPoint extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "point")
    private int point;

    @Column(name = "memo")
    private String memo;

    @Builder
    public RewardPoint(Long storeId, String phoneNumber, Long userId, int point, String memo) {
        this.storeId = storeId;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.point = point;
        this.memo = memo;
        this.createdAt = LocalDateTime.now();
    }

    public void usePoint( int usePoint ){
        if( this.point < usePoint ) throw new ApiException(ErrorCode.REWARD_POINT_NOT_ENOUGH);
        this.point -= usePoint;
    }

    public void addPoint( int earnPoint ) { this.point += earnPoint; }
}
