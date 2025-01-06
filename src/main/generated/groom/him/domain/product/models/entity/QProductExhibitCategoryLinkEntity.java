package groom.him.domain.product.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductExhibitCategoryLinkEntity is a Querydsl query type for ProductExhibitCategoryLinkEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductExhibitCategoryLinkEntity extends EntityPathBase<ProductExhibitCategoryLinkEntity> {

    private static final long serialVersionUID = 895832551L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductExhibitCategoryLinkEntity productExhibitCategoryLinkEntity = new QProductExhibitCategoryLinkEntity("productExhibitCategoryLinkEntity");

    public final groom.him.common.models.entity.QRegisterDateFields _super = new groom.him.common.models.entity.QRegisterDateFields(this);

    public final groom.him.domain.category.models.entity.QExhibitCategoryEntity exhibitCategory;

    public final QProductEntity product;

    public final NumberPath<Integer> productExhibitCategoryId = createNumber("productExhibitCategoryId", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public QProductExhibitCategoryLinkEntity(String variable) {
        this(ProductExhibitCategoryLinkEntity.class, forVariable(variable), INITS);
    }

    public QProductExhibitCategoryLinkEntity(Path<? extends ProductExhibitCategoryLinkEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductExhibitCategoryLinkEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductExhibitCategoryLinkEntity(PathMetadata metadata, PathInits inits) {
        this(ProductExhibitCategoryLinkEntity.class, metadata, inits);
    }

    public QProductExhibitCategoryLinkEntity(Class<? extends ProductExhibitCategoryLinkEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exhibitCategory = inits.isInitialized("exhibitCategory") ? new groom.him.domain.category.models.entity.QExhibitCategoryEntity(forProperty("exhibitCategory"), inits.get("exhibitCategory")) : null;
        this.product = inits.isInitialized("product") ? new QProductEntity(forProperty("product"), inits.get("product")) : null;
    }

}

