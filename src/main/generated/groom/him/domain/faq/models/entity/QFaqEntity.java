package groom.him.domain.faq.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFaqEntity is a Querydsl query type for FaqEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaqEntity extends EntityPathBase<FaqEntity> {

    private static final long serialVersionUID = 1553320830L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFaqEntity faqEntity = new QFaqEntity("faqEntity");

    public final StringPath answer = createString("answer");

    public final QFaqCategoryEntity faqCategory;

    public final NumberPath<Integer> faqId = createNumber("faqId", Integer.class);

    public final EnumPath<groom.him.core.common.enums.IsPublic> isPublic = createEnum("isPublic", groom.him.core.common.enums.IsPublic.class);

    public final StringPath prio = createString("prio");

    public final StringPath question = createString("question");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updDt = createDateTime("updDt", java.time.LocalDateTime.class);

    public QFaqEntity(String variable) {
        this(FaqEntity.class, forVariable(variable), INITS);
    }

    public QFaqEntity(Path<? extends FaqEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFaqEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFaqEntity(PathMetadata metadata, PathInits inits) {
        this(FaqEntity.class, metadata, inits);
    }

    public QFaqEntity(Class<? extends FaqEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.faqCategory = inits.isInitialized("faqCategory") ? new QFaqCategoryEntity(forProperty("faqCategory")) : null;
    }

}

