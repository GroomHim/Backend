package groom.him.domain.qa.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQaCategoryEntity is a Querydsl query type for QaCategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQaCategoryEntity extends EntityPathBase<QaCategoryEntity> {

    private static final long serialVersionUID = 1757840826L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQaCategoryEntity qaCategoryEntity = new QQaCategoryEntity("qaCategoryEntity");

    public final groom.him.common.models.entity.QRegisterDateFields _super = new groom.him.common.models.entity.QRegisterDateFields(this);

    public final ListPath<QaCategoryEntity, QQaCategoryEntity> children = this.<QaCategoryEntity, QQaCategoryEntity>createList("children", QaCategoryEntity.class, QQaCategoryEntity.class, PathInits.DIRECT2);

    public final BooleanPath isLeaf = createBoolean("isLeaf");

    public final QQaCategoryEntity parentQaCategory;

    public final NumberPath<Integer> qaCategoryId = createNumber("qaCategoryId", Integer.class);

    public final StringPath qaCategoryName = createString("qaCategoryName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public QQaCategoryEntity(String variable) {
        this(QaCategoryEntity.class, forVariable(variable), INITS);
    }

    public QQaCategoryEntity(Path<? extends QaCategoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQaCategoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQaCategoryEntity(PathMetadata metadata, PathInits inits) {
        this(QaCategoryEntity.class, metadata, inits);
    }

    public QQaCategoryEntity(Class<? extends QaCategoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentQaCategory = inits.isInitialized("parentQaCategory") ? new QQaCategoryEntity(forProperty("parentQaCategory"), inits.get("parentQaCategory")) : null;
    }

}

