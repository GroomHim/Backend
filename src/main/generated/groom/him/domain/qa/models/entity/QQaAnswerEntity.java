package groom.him.domain.qa.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQaAnswerEntity is a Querydsl query type for QaAnswerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQaAnswerEntity extends EntityPathBase<QaAnswerEntity> {

    private static final long serialVersionUID = -850038086L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQaAnswerEntity qaAnswerEntity = new QQaAnswerEntity("qaAnswerEntity");

    public final groom.him.common.models.entity.QAuditingFields _super = new groom.him.common.models.entity.QAuditingFields(this);

    public final StringPath answer = createString("answer");

    public final groom.him.domain.member.models.entity.QMemberEntity member;

    public final QQaEntity qa;

    public final NumberPath<Integer> qaAnswerId = createNumber("qaAnswerId", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDt = _super.udtDt;

    public QQaAnswerEntity(String variable) {
        this(QaAnswerEntity.class, forVariable(variable), INITS);
    }

    public QQaAnswerEntity(Path<? extends QaAnswerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQaAnswerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQaAnswerEntity(PathMetadata metadata, PathInits inits) {
        this(QaAnswerEntity.class, metadata, inits);
    }

    public QQaAnswerEntity(Class<? extends QaAnswerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new groom.him.domain.member.models.entity.QMemberEntity(forProperty("member"), inits.get("member")) : null;
        this.qa = inits.isInitialized("qa") ? new QQaEntity(forProperty("qa"), inits.get("qa")) : null;
    }

}

