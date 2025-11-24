package fooding.im.core.domain.reward;

import fooding.im.core.global.exception.ApiException;

public enum RewardStatus {
    // 적립, 취소, 사용완료
    EARNED("EARNED"),
    CANCELED("CANCELED"),
    USED("USED");

    private String name;

    private RewardStatus( String name ) { this.name = name; }

    public String getName() { return this.name; }

    public static RewardStatus fromString( String name ){
        for ( RewardStatus status: values() ){
            if( status.name.equalsIgnoreCase(name) ) return status;
        }
        throw new IllegalArgumentException();
    }
}
