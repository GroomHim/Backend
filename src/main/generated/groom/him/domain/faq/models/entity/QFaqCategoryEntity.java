package groom.him.domain.faq.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFaqCategoryEntity is a Querydsl query type for FaqCategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaqCategoryEntity extends EntityPathBase<FaqCategoryEntity> {

    private static final long serialVersionUID = -1912114404L;

    public static final QFaqCategoryEntity faqCategoryEntity = new QFaqCategoryEntity("faqCategoryEntity");

    public final NumberPath<Integer> faqCategoryId = createNumber("faqCategoryId", Integer.class);

    public final StringPath faqCategoryName = createString("faqCategoryName");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> udtDt = createDateTime("udtDt", java.time.LocalDateTime.class);

    public QFaqCategoryEntity(String variable) {
        super(FaqCategoryEntity.class, forVariable(variable));
    }

    public QFaqCategoryEntity(Path<? extends FaqCategoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFaqCategoryEntity(PathMetadata metadata) {
        super(FaqCategoryEntity.class, metadata);
    }

}

