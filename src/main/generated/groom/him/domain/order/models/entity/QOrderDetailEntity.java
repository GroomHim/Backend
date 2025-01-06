package groom.him.domain.order.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderDetailEntity is a Querydsl query type for OrderDetailEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderDetailEntity extends EntityPathBase<OrderDetailEntity> {

    private static final long serialVersionUID = 1276134687L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderDetailEntity orderDetailEntity = new QOrderDetailEntity("orderDetailEntity");

    public final QOrderEntity order;

    public final NumberPath<Integer> orderDetailId = createNumber("orderDetailId", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final groom.him.domain.product.models.entity.QProductEntity product;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QOrderDetailEntity(String variable) {
        this(OrderDetailEntity.class, forVariable(variable), INITS);
    }

    public QOrderDetailEntity(Path<? extends OrderDetailEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderDetailEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderDetailEntity(PathMetadata metadata, PathInits inits) {
        this(OrderDetailEntity.class, metadata, inits);
    }

    public QOrderDetailEntity(Class<? extends OrderDetailEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrderEntity(forProperty("order"), inits.get("order")) : null;
        this.product = inits.isInitialized("product") ? new groom.him.domain.product.models.entity.QProductEntity(forProperty("product"), inits.get("product")) : null;
    }

}

