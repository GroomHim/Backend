package groom.him.domain.search.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSearchEntity is a Querydsl query type for SearchEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSearchEntity extends EntityPathBase<SearchEntity> {

    private static final long serialVersionUID = -1072337764L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSearchEntity searchEntity = new QSearchEntity("searchEntity");

    public final groom.him.common.models.entity.QRegisterDateFields _super = new groom.him.common.models.entity.QRegisterDateFields(this);

    public final groom.him.domain.member.models.entity.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final NumberPath<Long> searchId = createNumber("searchId", Long.class);

    public final StringPath searchWord = createString("searchWord");

    public QSearchEntity(String variable) {
        this(SearchEntity.class, forVariable(variable), INITS);
    }

    public QSearchEntity(Path<? extends SearchEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSearchEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSearchEntity(PathMetadata metadata, PathInits inits) {
        this(SearchEntity.class, metadata, inits);
    }

    public QSearchEntity(Class<? extends SearchEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new groom.him.domain.member.models.entity.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

