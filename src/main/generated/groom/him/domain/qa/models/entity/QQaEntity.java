package groom.him.domain.qa.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQaEntity is a Querydsl query type for QaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQaEntity extends EntityPathBase<QaEntity> {

    private static final long serialVersionUID = 1755864092L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQaEntity qaEntity = new QQaEntity("qaEntity");

    public final groom.him.common.models.entity.QAuditingFields _super = new groom.him.common.models.entity.QAuditingFields(this);

    public final StringPath content = createString("content");

    public final groom.him.domain.member.models.entity.QMemberEntity member;

    public final QQaCategoryEntity qaCategory;

    public final NumberPath<Integer> qaId = createNumber("qaId", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final EnumPath<groom.him.domain.qa.models.enums.QaStatus> status = createEnum("status", groom.him.domain.qa.models.enums.QaStatus.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDt = _super.udtDt;

    public QQaEntity(String variable) {
        this(QaEntity.class, forVariable(variable), INITS);
    }

    public QQaEntity(Path<? extends QaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQaEntity(PathMetadata metadata, PathInits inits) {
        this(QaEntity.class, metadata, inits);
    }

    public QQaEntity(Class<? extends QaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new groom.him.domain.member.models.entity.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.qaCategory = inits.isInitialized("qaCategory") ? new QQaCategoryEntity(forProperty("qaCategory"), inits.get("qaCategory")) : null;
    }

}

