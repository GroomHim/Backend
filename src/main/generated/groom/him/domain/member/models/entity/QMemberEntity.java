package groom.him.domain.member.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = 1504765916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final groom.him.common.models.entity.QAuditingFields _super = new groom.him.common.models.entity.QAuditingFields(this);

    public final StringPath birth = createString("birth");

    public final StringPath ci = createString("ci");

    public final StringPath email = createString("email");

    public final EnumPath<groom.him.common.models.constant.Gender> gender = createEnum("gender", groom.him.common.models.constant.Gender.class);

    public final BooleanPath isCancel = createBoolean("isCancel");

    public final StringPath loginId = createString("loginId");

    public final NumberPath<Integer> memberId = createNumber("memberId", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final groom.him.domain.member.models.entity.data.QPassword password;

    public final StringPath phoneNumber = createString("phoneNumber");

    public final EnumPath<groom.him.domain.member.models.constant.Provider> provider = createEnum("provider", groom.him.domain.member.models.constant.Provider.class);

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final EnumPath<groom.him.common.models.constant.Role> role = createEnum("role", groom.him.common.models.constant.Role.class);

    public final groom.him.common.models.entity.QSkinTypeEntity skinTypeEntity;

    public final StringPath socialTokenId = createString("socialTokenId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDt = _super.udtDt;

    public QMemberEntity(String variable) {
        this(MemberEntity.class, forVariable(variable), INITS);
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberEntity(PathMetadata metadata, PathInits inits) {
        this(MemberEntity.class, metadata, inits);
    }

    public QMemberEntity(Class<? extends MemberEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.password = inits.isInitialized("password") ? new groom.him.domain.member.models.entity.data.QPassword(forProperty("password")) : null;
        this.skinTypeEntity = inits.isInitialized("skinTypeEntity") ? new groom.him.common.models.entity.QSkinTypeEntity(forProperty("skinTypeEntity")) : null;
    }

}

