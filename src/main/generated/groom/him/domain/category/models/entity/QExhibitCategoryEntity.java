package groom.him.domain.category.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExhibitCategoryEntity is a Querydsl query type for ExhibitCategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExhibitCategoryEntity extends EntityPathBase<ExhibitCategoryEntity> {

    private static final long serialVersionUID = -1625048577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExhibitCategoryEntity exhibitCategoryEntity = new QExhibitCategoryEntity("exhibitCategoryEntity");

    public final groom.him.common.models.entity.QAuditingFields _super = new groom.him.common.models.entity.QAuditingFields(this);

    public final ListPath<ExhibitCategoryEntity, QExhibitCategoryEntity> children = this.<ExhibitCategoryEntity, QExhibitCategoryEntity>createList("children", ExhibitCategoryEntity.class, QExhibitCategoryEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final NumberPath<Integer> exhibitCategoryId = createNumber("exhibitCategoryId", Integer.class);

    public final StringPath exhibitCategoryName = createString("exhibitCategoryName");

    public final BooleanPath isLeaf = createBoolean("isLeaf");

    public final QExhibitCategoryEntity parentExhibitCategory;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDt = _super.udtDt;

    public QExhibitCategoryEntity(String variable) {
        this(ExhibitCategoryEntity.class, forVariable(variable), INITS);
    }

    public QExhibitCategoryEntity(Path<? extends ExhibitCategoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExhibitCategoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExhibitCategoryEntity(PathMetadata metadata, PathInits inits) {
        this(ExhibitCategoryEntity.class, metadata, inits);
    }

    public QExhibitCategoryEntity(Class<? extends ExhibitCategoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentExhibitCategory = inits.isInitialized("parentExhibitCategory") ? new QExhibitCategoryEntity(forProperty("parentExhibitCategory"), inits.get("parentExhibitCategory")) : null;
    }

}

