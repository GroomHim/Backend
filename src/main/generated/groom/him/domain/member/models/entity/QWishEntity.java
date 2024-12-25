package groom.him.domain.member.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishEntity is a Querydsl query type for WishEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWishEntity extends EntityPathBase<WishEntity> {

    private static final long serialVersionUID = 1406383081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWishEntity wishEntity = new QWishEntity("wishEntity");

    public final groom.him.common.models.entity.QRegisterDateFields _super = new groom.him.common.models.entity.QRegisterDateFields(this);

    public final QMemberEntity member;

    public final groom.him.domain.product.models.entity.QProductEntity product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final NumberPath<Integer> wishId = createNumber("wishId", Integer.class);

    public QWishEntity(String variable) {
        this(WishEntity.class, forVariable(variable), INITS);
    }

    public QWishEntity(Path<? extends WishEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWishEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWishEntity(PathMetadata metadata, PathInits inits) {
        this(WishEntity.class, metadata, inits);
    }

    public QWishEntity(Class<? extends WishEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.product = inits.isInitialized("product") ? new groom.him.domain.product.models.entity.QProductEntity(forProperty("product"), inits.get("product")) : null;
    }

}

