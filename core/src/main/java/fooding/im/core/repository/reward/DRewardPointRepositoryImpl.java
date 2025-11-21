package fooding.im.core.repository.reward;

import fooding.im.core.domain.reward.RewardLog;
import fooding.im.core.domain.reward.RewardPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DRewardPointRepositoryImpl implements DRewardPointRepository {
    private final DatabaseClient databaseClient;
    @Override
    public Mono<Page<RewardPoint>> list(
            String searchString,
            Pageable pageable,
            Long storeId,
            String phoneNumber
    ) {
        StringBuilder countQuery = new StringBuilder("SELECT COUNT(*) FROM reward_point WHERE deleted=false");
        StringBuilder query = new StringBuilder("SELECT * FROM reward_point WHERE deleted=false");

        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(query.toString());

        if( storeId != null ) {
            query.append(" AND store_id = :storeId");
            countQuery.append(" AND store_id = :storeId");
            spec = spec.bind("storeId", storeId);
        }
        if( phoneNumber != null ){
            query.append(" AND phone_number = :phoneNumber");
            countQuery.append(" AND phone_number = :phoneNumber");
            spec = spec.bind("phoneNumber", phoneNumber);
        }
        query.append(" LIMIT :limit OFFSET :offset");
        spec = spec
                .bind("limit", pageable.getPageSize())
                .bind("offset", pageable.getOffset());

        Flux<RewardPoint> dataFlux = spec
                .map( ( row, metedata ) -> {
                    RewardPoint rewardPoint = new RewardPoint();
                    rewardPoint.setId( row.get("id", Long.class ) );
                    rewardPoint.setStoreId( row.get("store_id", Long.class ) );
                    rewardPoint.setPhoneNumber( row.get("phone_number", String.class ) );
                    rewardPoint.setUserId( row.get("user_id", Long.class ) );
                    rewardPoint.setPoint( row.get("point", Integer.class ) );
                    rewardPoint.setMemo( row.get("memo", String.class ) );

                    return rewardPoint;
                }).all();
        Mono<Long> totalCountMono = databaseClient.sql( countQuery.toString() )
                .map( ( row, metadata ) -> row.get( 0, Long.class )).one();
        return Mono.zip( dataFlux.collectList(), totalCountMono)
                .map( tuple -> {
                    List<RewardPoint> content = tuple.getT1();
                    long totalCount = tuple.getT2();
                    return new PageImpl<>( content, pageable, totalCount );
                } );
    }
}
