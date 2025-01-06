package groom.him.domain.product.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductEntity is a Querydsl query type for ProductEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductEntity extends EntityPathBase<ProductEntity> {

    private static final long serialVersionUID = -1241396112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final groom.him.common.models.entity.QAuditingFields _super = new groom.him.common.models.entity.QAuditingFields(this);

    public final NumberPath<Integer> brandId = createNumber("brandId", Integer.class);

    public final groom.him.domain.category.models.entity.QCategoryEntity category;

    public final NumberPath<Integer> discountedPrice = createNumber("discountedPrice", Integer.class);

    public final NumberPath<Float> discountRate = createNumber("discountRate", Float.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath ingredients = createString("ingredients");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final StringPath productName = createString("productName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDt = _super.udtDt;

    public QProductEntity(String variable) {
        this(ProductEntity.class, forVariable(variable), INITS);
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductEntity(PathMetadata metadata, PathInits inits) {
        this(ProductEntity.class, metadata, inits);
    }

    public QProductEntity(Class<? extends ProductEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new groom.him.domain.category.models.entity.QCategoryEntity(forProperty("category"), inits.get("category")) : null;
    }

}

