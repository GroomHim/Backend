package groom.him.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import groom.him.domain.member.models.entity.QMemberEntity;
import groom.him.domain.member.models.entity.QWishEntity;
import groom.him.domain.order.models.entity.QOrderDetailEntity;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.QProductEntity;
import groom.him.domain.product.models.entity.QProductSkinTypeLinkEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductEntity> findMemberWishProductBriefBySkinType(Integer memberId,
        Boolean isSkinType, Pageable pageable) {
        QProductEntity product = QProductEntity.productEntity;
        QWishEntity wish = QWishEntity.wishEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;

        int limit = pageable.getPageSize() + 1;
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

        List<ProductEntity> content = jpaQueryFactory
            .select(product)
            .from(wish)
            .leftJoin(wish.product, product)
            .where(builder)
            .orderBy(wish.regDt.asc())
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductEntity> findProductListBySkinTypeOrderByQuantity(Pageable pageable,
        Integer skinType) {
        QProductEntity product = QProductEntity.productEntity;
        QProductSkinTypeLinkEntity productSkinTypeLink = QProductSkinTypeLinkEntity.productSkinTypeLinkEntity;
        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductEntity> content = jpaQueryFactory
            .select(product)
            .from(product)
            .join(productSkinTypeLink)
            .on(productSkinTypeLink.product.productId.eq(product.productId))
            .join(orderDetail).on(orderDetail.product.productId.eq(product.productId))
            .where(productSkinTypeLink.skinType.skinTypeId.eq(skinType))
            .groupBy(product.productId)
            .orderBy(orderDetail.quantity.sum().desc())
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ProductEntity> findProductListByPriceRange(Pageable pageable, Integer minPrice,
        Integer maxPrice) {
        QProductEntity product = QProductEntity.productEntity;
        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;

        int limit = pageable.getPageSize() + 1;

        List<ProductEntity> content = jpaQueryFactory
            .select(product)
            .from(product)
            .join(orderDetail).on(orderDetail.product.productId.eq(product.productId))
            .where(product.discountedPrice.between(minPrice, maxPrice))
            .groupBy(product.productId)
            .orderBy(orderDetail.quantity.sum().desc())
            .offset(pageable.getOffset())
            .limit(limit)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}