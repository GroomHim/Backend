package groom.him.domain.product.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductSkinTypeLinkEntity is a Querydsl query type for ProductSkinTypeLinkEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductSkinTypeLinkEntity extends EntityPathBase<ProductSkinTypeLinkEntity> {

    private static final long serialVersionUID = -837052607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductSkinTypeLinkEntity productSkinTypeLinkEntity = new QProductSkinTypeLinkEntity("productSkinTypeLinkEntity");

    public final groom.him.common.models.entity.QRegisterDateFields _super = new groom.him.common.models.entity.QRegisterDateFields(this);

    public final QProductEntity product;

    public final NumberPath<Integer> productSkinTypeId = createNumber("productSkinTypeId", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final groom.him.common.models.entity.QSkinTypeEntity skinType;

    public QProductSkinTypeLinkEntity(String variable) {
        this(ProductSkinTypeLinkEntity.class, forVariable(variable), INITS);
    }

    public QProductSkinTypeLinkEntity(Path<? extends ProductSkinTypeLinkEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductSkinTypeLinkEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductSkinTypeLinkEntity(PathMetadata metadata, PathInits inits) {
        this(ProductSkinTypeLinkEntity.class, metadata, inits);
    }

    public QProductSkinTypeLinkEntity(Class<? extends ProductSkinTypeLinkEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProductEntity(forProperty("product"), inits.get("product")) : null;
        this.skinType = inits.isInitialized("skinType") ? new groom.him.common.models.entity.QSkinTypeEntity(forProperty("skinType")) : null;
    }

}

