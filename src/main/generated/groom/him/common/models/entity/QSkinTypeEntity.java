package groom.him.common.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSkinTypeEntity is a Querydsl query type for SkinTypeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSkinTypeEntity extends EntityPathBase<SkinTypeEntity> {

    private static final long serialVersionUID = 1766612406L;

    public static final QSkinTypeEntity skinTypeEntity = new QSkinTypeEntity("skinTypeEntity");

    public final QRegisterDateFields _super = new QRegisterDateFields(this);

    public final StringPath description = createString("description");

    public final NumberPath<java.math.BigDecimal> rate = createNumber("rate", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final NumberPath<Integer> skinTypeId = createNumber("skinTypeId", Integer.class);

    public final StringPath skinTypeName = createString("skinTypeName");

    public QSkinTypeEntity(String variable) {
        super(SkinTypeEntity.class, forVariable(variable));
    }

    public QSkinTypeEntity(Path<? extends SkinTypeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSkinTypeEntity(PathMetadata metadata) {
        super(SkinTypeEntity.class, metadata);
    }

}

