package groom.him.domain.order.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderEntity is a Querydsl query type for OrderEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderEntity extends EntityPathBase<OrderEntity> {

    private static final long serialVersionUID = 1918256558L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderEntity orderEntity = new QOrderEntity("orderEntity");

    public final groom.him.common.models.entity.QRegisterDateFields _super = new groom.him.common.models.entity.QRegisterDateFields(this);

    public final groom.him.domain.member.models.entity.QMemberEntity member;

    public final NumberPath<Integer> orderId = createNumber("orderId", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public QOrderEntity(String variable) {
        this(OrderEntity.class, forVariable(variable), INITS);
    }

    public QOrderEntity(Path<? extends OrderEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderEntity(PathMetadata metadata, PathInits inits) {
        this(OrderEntity.class, metadata, inits);
    }

    public QOrderEntity(Class<? extends OrderEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new groom.him.domain.member.models.entity.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

