package groom.him.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groom.him.domain.member.models.entity.QMemberEntity;
import groom.him.domain.member.models.entity.QWishEntity;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.QProductEntity;
import groom.him.domain.product.models.entity.QProductSkinTypeLinkEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import static com.querydsl.core.types.dsl.Expressions.stringPath;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private static QProductEntity product = QProductEntity.productEntity;
    @Override
    public List<ProductEntity> findMemberWishProductBriefBySkinType(Integer memberId,
        Boolean isSkinType) {
        QWishEntity wish = QWishEntity.wishEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if (isSkinType) {
            builder.and(wish.product.productId.in(
                JPAExpressions
                    .select(productSkinTypeLink.product.productId)
                    .from(productSkinTypeLink)
                    .join(member)
                    .on(member.skinTypeEntity.skinTypeId.eq(
                        productSkinTypeLink.skinType.skinTypeId))
                    .where(member.memberId.eq(memberId))
            ));
        } else {
            builder.and(wish.member.memberId.eq(memberId));
        }
        return jpaQueryFactory
            .select(product)
            .from(wish)
            .leftJoin(wish.product, product)
            .where(builder)
            .orderBy(wish.regDt.asc())
            .fetch();
    }

    public List<ProductEntity> findSearchProduct(String word){
        return jpaQueryFactory.selectFrom(product)
                .where(product.productName.contains(word))
                .orderBy(product.regDt.desc())
                .fetch();
    }

}