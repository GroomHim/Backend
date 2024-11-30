package groom.him.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groom.him.domain.member.models.entity.QMemberEntity;
import groom.him.domain.member.models.entity.QWishEntity;
import groom.him.domain.order.models.entity.QOrderDetailEntity;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.QProductEntity;
import groom.him.domain.product.models.entity.QProductSkinTypeLinkEntity;
import groom.him.domain.qa.models.dto.response.QaResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductEntity> findMemberWishProductBriefBySkinType(Integer memberId,
        Boolean isSkinType) {
        QProductEntity product = QProductEntity.productEntity;
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

    @Override
    public List<ProductEntity> findProductListBySkinTypeOrderByQuantity(Integer skinType) {
        QProductEntity product = QProductEntity.productEntity;
        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;
        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;

        return jpaQueryFactory
            .select(product)
            .from(product)
            .join(productSkinTypeLink)
            .on(productSkinTypeLink.product.productId.eq(product.productId))
            .join(orderDetail).on(orderDetail.product.productId.eq(product.productId))
            .where(productSkinTypeLink.skinType.skinTypeId.eq(skinType))
            .groupBy(product.productId)
            .orderBy(orderDetail.quantity.sum().desc())
            .limit(20)
            .fetch();
    }
}