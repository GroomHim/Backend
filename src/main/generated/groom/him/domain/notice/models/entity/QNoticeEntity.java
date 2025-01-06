package groom.him.domain.notice.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeEntity is a Querydsl query type for NoticeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeEntity extends EntityPathBase<NoticeEntity> {

    private static final long serialVersionUID = 1213368988L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeEntity noticeEntity = new QNoticeEntity("noticeEntity");

    public final groom.him.common.models.entity.QAuditingFields _super = new groom.him.common.models.entity.QAuditingFields(this);

    public final StringPath content = createString("content");

    public final EnumPath<groom.him.core.common.enums.IsPublic> isPublic = createEnum("isPublic", groom.him.core.common.enums.IsPublic.class);

    public final groom.him.domain.member.models.entity.QMemberEntity member;

    public final NumberPath<Integer> noticeId = createNumber("noticeId", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDt = _super.udtDt;

    public QNoticeEntity(String variable) {
        this(NoticeEntity.class, forVariable(variable), INITS);
    }

    public QNoticeEntity(Path<? extends NoticeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeEntity(PathMetadata metadata, PathInits inits) {
        this(NoticeEntity.class, metadata, inits);
    }

    public QNoticeEntity(Class<? extends NoticeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new groom.him.domain.member.models.entity.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

