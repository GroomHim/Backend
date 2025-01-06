package groom.him.common.models.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegisterDateFields is a Querydsl query type for RegisterDateFields
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QRegisterDateFields extends EntityPathBase<RegisterDateFields> {

    private static final long serialVersionUID = 748361126L;

    public static final QRegisterDateFields registerDateFields = new QRegisterDateFields("registerDateFields");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public QRegisterDateFields(String variable) {
        super(RegisterDateFields.class, forVariable(variable));
    }

    public QRegisterDateFields(Path<? extends RegisterDateFields> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegisterDateFields(PathMetadata metadata) {
        super(RegisterDateFields.class, metadata);
    }

}

